package com.votes.domain.associate.api.v1.controller;

import com.votes.domain.associate.api.v1.controller.request.AssociateVoteDto;
import com.votes.domain.associate.service.AssociateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/associate")
@Slf4j
@RequiredArgsConstructor
public class AssociateController {

    private final AssociateService associateService;

    @PostMapping("/vote")
    public ResponseEntity<?> voteForAgenda(@Valid @RequestBody AssociateVoteDto request) {
        log.info("AssociateController - voteForAgenda - Start voting.");
        associateService.doVote(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
