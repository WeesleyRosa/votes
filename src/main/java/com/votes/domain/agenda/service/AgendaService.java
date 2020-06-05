package com.votes.domain.agenda.service;

import com.votes.config.exception.AgendaException;
import com.votes.config.MessagesUtils;
import com.votes.config.exception.enumerator.ErrorCodeEnum;
import com.votes.domain.agenda.api.v1.controller.request.AgendaRequestDto;
import com.votes.domain.agenda.api.v1.controller.response.AgendaVoteResponseDto;
import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import com.votes.domain.agenda.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final MessagesUtils messagesUtils;
    public static final Long DEFAULT_VOTING_TIME = 60000L;

    public List<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }

    public AgendaVoteResponseDto getAgendaByAgendaIdentifierOrThrow(Long agendaIdentifier) {
        log.info("AgendaService - getAgendaByAgendaIdentifierOrThrow - Getting agenda by id.");
        return createAgendaVotedResponse(getAgendaById(agendaIdentifier).orElseThrow(this::agendaNotFoundException));
    }

    public Optional<Agenda> getAgendaById(Long agendaIdentifier) {
        return agendaRepository.findByAgendaIdentifierAndStatusIs(agendaIdentifier, AgendaStatus.VOTE_CLOSED);
    }

    private AgendaException agendaNotFoundException() {
        return new AgendaException(HttpStatus.NOT_FOUND, ErrorCodeEnum.AGENDA_NOT_FOUND,
                messagesUtils.getCodeAndMessage("agenda.not.found")[1]);
    }

    public void createAgenda(AgendaRequestDto agendaRequestDto) {
        if(agendaRequestDto.getVotingTime() == null){
            log.info("AgendaService - createAgenda - Creating not started agenda.");
            saveNotStartedAgenda(agendaRequestDto);
        }
        else {
            log.info("AgendaService - createAgenda - Creating started agenda.");
            saveStartedAgenda(agendaRequestDto);
        }
    }

    public void startAgenda(Long agendaIdentifier) {
        log.info("AgendaService - startAgenda - Getting agenda by identifier.");
        Optional<Agenda> agendaOpt = agendaRepository.findByAgendaIdentifier(agendaIdentifier);
        agendaOpt.ifPresent(agenda -> {
            log.info("AgendaService - startAgenda - Starting vote.");
            handleObjectToStartVote(agenda);
            agendaRepository.save(agenda);
            log.info("AgendaService - startAgenda - Finishing vote.");
            runTask(agenda);
        });
    }

    private void runTask(Agenda agenda) {
        TimerTask task = new TimerTask() {
            public void run() {
                Agenda agendaOpt = agendaRepository.findByAgendaIdentifier(agenda.getAgendaIdentifier()).orElse(new Agenda());
                agendaOpt.setVoteEndedAt(LocalDateTime.now());
                agendaOpt.setStatus(AgendaStatus.VOTE_CLOSED);
                agendaRepository.save(agendaOpt);
                log.info("AgendaService - runTask - Task terminated.");
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, agenda.getVotingTime());
    }

    private static void handleObjectToStartVote(Agenda agenda) {
        agenda.setStatus(AgendaStatus.VOTING);
        agenda.setVoteStartedAt(LocalDateTime.now());
    }

    private void saveStartedAgenda(AgendaRequestDto agendaRequestDto) {
        agendaRepository.save(createStartedAgendaObject(agendaRequestDto));
    }

    private void saveNotStartedAgenda(AgendaRequestDto agendaRequestDto) {
        agendaRepository.save(createNotStartedAgendaObject(agendaRequestDto));
    }

    private Agenda createStartedAgendaObject(AgendaRequestDto agendaRequest) {
        return Agenda.builder()
                .subject(agendaRequest.getSubject())
                .createdAt(LocalDateTime.now())
                .voteStartedAt(LocalDateTime.now())
                .status(AgendaStatus.VOTING)
                .votingTime(agendaRequest.getVotingTime())
                .agendaIdentifier(agendaRequest.getAgendaIdentifier())
                .build();
    }

    private Agenda createNotStartedAgendaObject(AgendaRequestDto agendaRequest) {
        return Agenda.builder()
                .subject(agendaRequest.getSubject())
                .status(AgendaStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .votingTime(agendaRequest.getVotingTime())
                .agendaIdentifier(DEFAULT_VOTING_TIME)
                .build();
    }

    private AgendaVoteResponseDto createAgendaVotedResponse(Agenda agenda) {
        return AgendaVoteResponseDto.builder()
                .status(agenda.getStatus())
                .subject(agenda.getSubject())
                .voteEndedAt(agenda.getVoteEndedAt())
                .votes(agenda.getVotes())
                .voteStartedAt(agenda.getVoteStartedAt())
                .build();
    }
}
