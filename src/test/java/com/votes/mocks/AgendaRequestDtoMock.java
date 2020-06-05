package com.votes.mocks;

import com.votes.domain.agenda.api.v1.controller.request.AgendaRequestDto;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;

public class AgendaRequestDtoMock {

    public static AgendaRequestDto createStartedAgendaRequestDto() {
        return AgendaRequestDto.builder()
                .agendaIdentifier(1L)
                .agendaStatus(AgendaStatus.CREATED)
                .subject("Teste")
                .votingTime(1000L)
                .build();
    }

    public static AgendaRequestDto createNotStartedAgendaRequestDto() {
        return AgendaRequestDto.builder()
                .agendaIdentifier(1L)
                .agendaStatus(AgendaStatus.VOTING)
                .subject("Teste")
                .build();
    }
}
