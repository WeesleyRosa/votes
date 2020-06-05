package com.votes.domain.associate.repository;

import com.votes.domain.associate.entities.Associate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AssociateRepository extends MongoRepository<Associate, String> {

    Optional<Associate> findByDocumentNumber(String documentNumber);
}
