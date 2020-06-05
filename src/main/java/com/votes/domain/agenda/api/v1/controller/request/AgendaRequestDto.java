package com.votes.domain.agenda.api.v1.controller.request;

import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaRequestDto {

    @NotBlank
    private String subject;
    @NonNull
    private Long agendaIdentifier;
    private Long votingTime;
    private AgendaStatus agendaStatus;
}
