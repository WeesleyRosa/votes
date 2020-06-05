package com.votes.domain.agenda.entities;

import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "agenda")
public class Agenda {

    @Id
    private String id;
    @Indexed
    private Long agendaIdentifier;
    private String subject;
    private LocalDateTime voteStartedAt;
    private LocalDateTime voteEndedAt;
    private LocalDateTime createdAt;
    private AgendaStatus status;
    private Long votingTime;
    /**
     * Map of associates who voted this agenda.. Key = documentNumber and value = value of the vote
     */
    private Map<String, Boolean> votes = new HashMap<>();;

    public Agenda(Long agendaIdentifier, String subject, LocalDateTime voteStartedAt, LocalDateTime createdAt, AgendaStatus status, Long votingTime) {
        this.agendaIdentifier = agendaIdentifier;
        this.subject = subject;
        this.voteStartedAt = voteStartedAt;
        this.createdAt = createdAt;
        this.status = status;
        this.votingTime = votingTime;
        this.votes = votes;
    }

    public void startAgenda() {
        this.voteStartedAt = LocalDateTime.now();
        this.status = AgendaStatus.VOTING;
    }
}
