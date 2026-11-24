package io.ivanbyone.dictionary_service.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import io.ivanbyone.dictionary_service.entity.enums.Blockchain;
import io.ivanbyone.dictionary_service.entity.enums.DictionaryType;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Table(name = "black_dictionary")
public class BlackDictionary extends BaseEntity {

    private Blockchain blockchain;

    @Column("b_type")
    private DictionaryType type;

    @Column("b_value")
    private String value;

    @Column("b_reason")
    private String reason;
}
