package com.votes.domain.agenda.repository;

import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AgendaRepository extends MongoRepository<Agenda, String> {

    Optional<Agenda> findByAgendaIdentifier(Long agendaIdentifier);

    Optional<Agenda> findByAgendaIdentifierAndStatusIs(Long agendaIdentifier, AgendaStatus status);
}
