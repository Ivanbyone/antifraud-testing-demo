package io.ivanbyone.dictionary_service.repository;

import org.springframework.data.repository.ListCrudRepository;

import io.ivanbyone.dictionary_service.entity.Outbox;

public interface OutboxRepository extends ListCrudRepository<Outbox, Long> {
    
}
