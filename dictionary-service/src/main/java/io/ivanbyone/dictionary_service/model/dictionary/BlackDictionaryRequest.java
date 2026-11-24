package io.ivanbyone.dictionary_service.model.dictionary;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BlackDictionaryRequest(
    @JsonProperty("BlackDictionaryInput")
    List<BlackDictionaryInput> blackDictionaryInputs
) {
}
