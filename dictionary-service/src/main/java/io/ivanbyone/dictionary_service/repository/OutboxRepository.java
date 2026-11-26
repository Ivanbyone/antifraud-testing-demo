package io.ivanbyone.dictionary_service.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import io.ivanbyone.dictionary_service.entity.Outbox;
import java.util.List;
import io.ivanbyone.dictionary_service.entity.enums.OutboxStatus;


public interface OutboxRepository extends ListCrudRepository<Outbox, Long> {
    
    @Query("SELECT * FROM outbox WHERE o_status = :status")
    List<Outbox> findByStatus(@Param("status") OutboxStatus status);

    @Query("UPDATE outbox SET o_status = :status WHERE id IN (:ids)")
    void updateStatuses(@Param("status") OutboxStatus status, @Param("ids") List<Long> ids);
}
