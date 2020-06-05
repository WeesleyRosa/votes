package com.votes.agenda.service;

import com.votes.config.MessagesUtils;
import com.votes.domain.agenda.api.v1.controller.request.AgendaRequestDto;
import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import com.votes.domain.agenda.repository.AgendaRepository;
import com.votes.domain.agenda.service.AgendaService;
import com.votes.mocks.AgendaMock;
import com.votes.mocks.AgendaRequestDtoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @InjectMocks
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private MessagesUtils messagesUtils;

    AgendaRequestDto startedAgendaRequestDto = AgendaRequestDtoMock.createStartedAgendaRequestDto();
    AgendaRequestDto notStartedAgendaRequestDto = AgendaRequestDtoMock.createNotStartedAgendaRequestDto();

    @Test
    void givenAgendaRequestDto_whenCreatingAgenda_ThenSaveStartedAgenda() {
        agendaService.createAgenda(startedAgendaRequestDto);
        verify(agendaRepository, times(1)).save(any());
    }

    @Test
    void givenAgendaRequestDto_whenCreatingAgenda_ThenSaveNotStartedAgenda() {
        agendaService.createAgenda(notStartedAgendaRequestDto);
        verify(agendaRepository, times(1)).save(any());
    }

    @Test
    void givenAgendaIdentifier_whenStartingAgenda_ThenStartAgenda() {
        Agenda agenda = AgendaMock.createAgenda();

        given(agendaRepository.findByAgendaIdentifier(agenda.getAgendaIdentifier())).willReturn(Optional.of(agenda));
        agendaService.startAgenda(notStartedAgendaRequestDto.getAgendaIdentifier());

        verify(agendaRepository, times(1)).findByAgendaIdentifier(any());
        verify(agendaRepository, times(1)).save(any());
        assertEquals(AgendaStatus.VOTING, agenda.getStatus());
        assertNotEquals( null, agenda.getVoteStartedAt());
    }
}
