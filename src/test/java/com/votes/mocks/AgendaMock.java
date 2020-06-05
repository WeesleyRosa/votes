package com.votes.mocks;

import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;

import java.time.LocalDateTime;

public class AgendaMock {

    public static Agenda createAgenda() {
        return Agenda.builder()
                .agendaIdentifier(1L)
                .votingTime(1000L)
                .createdAt(LocalDateTime.now())
                .status(AgendaStatus.CREATED)
                .subject("Teste")
                .id("5d4a15d1a959c40001ac4c98")
                .build();
    }
}
