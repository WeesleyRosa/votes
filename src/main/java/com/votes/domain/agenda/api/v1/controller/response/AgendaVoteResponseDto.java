package com.votes.domain.agenda.api.v1.controller.response;

import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaVoteResponseDto {

    private LocalDateTime voteStartedAt;
    private LocalDateTime voteEndedAt;
    private AgendaStatus status;
    private String subject;
    /**
     * Map of associates who voted this agenda.. Key = documentNumber and value = value of the vote
     */
    private Map<String, Boolean> votes = new HashMap<>();;
}
