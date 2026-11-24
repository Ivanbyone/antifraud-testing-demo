package io.ivanbyone.dictionary_service.model;

public record CreationOutput(
    int total,
    int duplicated,
    int created
) {
    
}
