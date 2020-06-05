package com.votes.config;

import com.votes.domain.agenda.entities.Agenda;
import com.votes.domain.agenda.entities.enumerator.AgendaStatus;
import com.votes.domain.agenda.repository.AgendaRepository;
import com.votes.domain.associate.entities.Associate;
import com.votes.domain.associate.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class Instantiation implements CommandLineRunner {

    private final AgendaRepository agendaRepository;
    private final AssociateRepository associateRepository;

    @Override
    public void run(String... args) throws Exception {

        agendaRepository.deleteAll();
        associateRepository.deleteAll();

        Agenda agenda1 = new Agenda(1L, "Assunto 1", null, LocalDateTime.now(), AgendaStatus.CREATED, 120000L);
        Agenda agenda2 = new Agenda(2L, "Assunto 2", null, LocalDateTime.now(), AgendaStatus.CREATED, 180000L);
        Agenda agenda3 = new Agenda(3L, "Assunto 3", null, LocalDateTime.now(), AgendaStatus.CREATED, 190000L);
        Agenda agenda4 = new Agenda(4L, "Assunto 4", null, LocalDateTime.now(), AgendaStatus.CREATED, 280000L);
        Agenda agenda5 = new Agenda(5L, "Assunto 5", null, LocalDateTime.now(), AgendaStatus.CREATED, 80000L);
        Agenda agenda6 = new Agenda(6L, "Assunto 6", null, LocalDateTime.now(), AgendaStatus.CREATED, 580000L);

        Associate associate1 = new Associate("81879830000", "User 1", "email1", "phone1", LocalDate.now());
        Associate associate2 = new Associate("43107305073", "User 2", "email2", "phone2", LocalDate.now());
        Associate associate3 = new Associate("54009281022", "User 3", "email3", "phone3", LocalDate.now());
        Associate associate4 = new Associate("34187695001", "User 4", "email4", "phone4", LocalDate.now());
        Associate associate5 = new Associate("80887160042", "User 5", "email5", "phone5", LocalDate.now());

        agendaRepository.saveAll(Arrays.asList(agenda1, agenda2, agenda3, agenda4, agenda5, agenda6));
        associateRepository.saveAll(Arrays.asList(associate1, associate2, associate3, associate4, associate5));

    }
}
