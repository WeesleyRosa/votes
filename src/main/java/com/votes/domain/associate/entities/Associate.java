package com.votes.domain.associate.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "associate")
public class Associate {

    @Id
    private String id;
    @Indexed
    private String documentNumber;
    @Indexed
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    /**
     * Map of voted agendas. Key = agendaIdentifier and value = value of the vote
     */
    private Map<Long, Boolean> votedAgendas = new HashMap<>();

    public Associate(String documentNumber, String fullName, String email, String phoneNumber, LocalDate birthday) {
        this.documentNumber = documentNumber;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }
}
