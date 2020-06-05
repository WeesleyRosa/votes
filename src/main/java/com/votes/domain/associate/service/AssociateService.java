package com.votes.domain.associate.service;

import com.votes.config.MessagesUtils;
import com.votes.config.exception.UserVoteException;
import com.votes.config.exception.enumerator.ErrorCodeEnum;
import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import com.votes.domain.agenda.repository.AgendaRepository;
import com.votes.domain.associate.api.v1.controller.request.AssociateVoteDto;
import com.votes.domain.associate.client.CpfValidationClient;
import com.votes.domain.associate.client.response.CpfValidationResponse;
import com.votes.domain.associate.entities.Associate;
import com.votes.domain.associate.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssociateService {

    private final AssociateRepository associateRepository;
    private final AgendaRepository agendaRepository;
    private final CpfValidationClient cpfValidationClient;
    private final MessagesUtils messagesUtils;

    public CpfValidationResponse validateCpf(AssociateVoteDto request) {
        log.info("AssociateService - CpfValidationResponse - Sending request to validate cpf.");
        return cpfValidationClient.validateCpf(request.getDocumentNumber());
    }

    public void doVote(AssociateVoteDto request) {
        log.info("AssociateService - doVote - Getting objects from database.");
        if(validateCpf(request).getStatus().equalsIgnoreCase("UNABLE_TO_VOTE")) {
            log.info("AssociateService - doVote - Cpf unable to vote.");
            throw new UserVoteException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.USER_UNABLE_TO_VOTE,
                    messagesUtils.getCodeAndMessage("user.vote")[1]);
        }
        Optional<Agenda> agenda = agendaRepository.findByAgendaIdentifierAndStatusIs(request.getAgendaIdentifier(), AgendaStatus.VOTING);
        agenda.ifPresent(agendaObj -> {
            log.info("AssociateService - doVote - Agenda found.");
            saveAgendaVote(agendaObj, request);
            saveAssociateVote(associateRepository.findByDocumentNumber(request.getDocumentNumber()), request);
        });
    }

    private void saveAgendaVote(Agenda agenda, AssociateVoteDto request) {
        agenda.getVotes().put(request.getDocumentNumber(), request.getVote());
        log.info("AssociateService - saveAgendaVote - Saving new vote in database. associate voting: " + request.getDocumentNumber());
        agendaRepository.save(agenda);
    }

    private void saveAssociateVote(Optional<Associate> associate, AssociateVoteDto request) {
        associate.ifPresent(associateObj -> {
            associateObj.getVotedAgendas().put(request.getAgendaIdentifier(), request.getVote());
            log.info("AssociateService - saveAssociateVote - Saving new vote in database. voted agenda: " + request.getAgendaIdentifier());
            associateRepository.save(associateObj);
        } );
    }
}
