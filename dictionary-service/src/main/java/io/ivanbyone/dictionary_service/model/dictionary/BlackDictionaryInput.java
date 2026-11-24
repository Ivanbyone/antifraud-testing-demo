package io.ivanbyone.dictionary_service.model.dictionary;

import io.ivanbyone.dictionary_service.entity.enums.Blockchain;
import io.ivanbyone.dictionary_service.entity.enums.DictionaryType;

public record BlackDictionaryInput(
    Blockchain blockchain,
    DictionaryType dictionaryType,
    String value,
    String reason
) {
    
}
