package com.votes.domain.associate.api.v1.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociateVoteDto {

    @NotBlank
    private String documentNumber;
    @NotNull
    private Long agendaIdentifier;
    @NotNull
    private Boolean vote;

}
