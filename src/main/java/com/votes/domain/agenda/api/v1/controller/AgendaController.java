package com.votes.domain.agenda.api.v1.controller;

import com.votes.domain.agenda.api.v1.controller.request.AgendaRequestDto;
import com.votes.domain.agenda.api.v1.controller.response.AgendaVoteResponseDto;
import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.service.AgendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/agenda")
@Slf4j
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping("/create/agenda")
    public ResponseEntity<?> createAgenda(@Valid @RequestBody AgendaRequestDto request) {
        log.info("AgendaController - POST - createAgenda - Start creating agenda.");
        agendaService.createAgenda(request);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping("/start/{agendaIdentifier}")
    public ResponseEntity<?> startAgenda(@PathVariable Long agendaIdentifier) {
        log.info("AgendaController - POST - startAgenda - Begin start agenda.");
        agendaService.startAgenda(agendaIdentifier);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @GetMapping
    public ResponseEntity<List<Agenda>> getAllAgendas() {
        log.info("AgendaController - GET - getAllAgendas - Start getting all agendas.");
        return ResponseEntity.ok(agendaService.getAllAgendas());
    }

    @GetMapping("/result/{agendaIdentifier}")
    public ResponseEntity<AgendaVoteResponseDto> getAgendaById(@PathVariable Long agendaIdentifier) {
        log.info("AgendaController - GET - getAgendaById - Start getting agenda by id.");
        return ResponseEntity.ok(agendaService.getAgendaByAgendaIdentifierOrThrow(agendaIdentifier));
    }
}
