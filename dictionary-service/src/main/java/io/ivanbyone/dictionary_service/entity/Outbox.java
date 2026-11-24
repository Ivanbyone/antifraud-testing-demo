package io.ivanbyone.dictionary_service.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import io.ivanbyone.dictionary_service.entity.enums.OutboxStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("outbox")
public class Outbox extends BaseEntity {
    @Column("queue_name")
    private String queueName;

    @Column("o_message")
    private String message;

    @Column("o_status")
    private OutboxStatus status;
}
