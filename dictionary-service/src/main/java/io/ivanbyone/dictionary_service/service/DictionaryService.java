package io.ivanbyone.dictionary_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ivanbyone.dictionary_service.entity.BlackDictionary;
import io.ivanbyone.dictionary_service.model.CreationOutput;
import io.ivanbyone.dictionary_service.model.dictionary.BlackDictionaryInput;
import io.ivanbyone.dictionary_service.model.errors.InvalidInputException;
import io.ivanbyone.dictionary_service.repository.BlackDictionaryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private static final int BATCH_LIMIT = 10;
    private final BlackDictionaryRepository blackRepository;
    
    @Transactional
    public CreationOutput create(List<BlackDictionaryInput> input) {
        int inputSize = input.size();

        if (input == null || input.isEmpty() || inputSize > BATCH_LIMIT) {
            throw new InvalidInputException(
                String.format("Список должен содержать от 1 до %d значений. Передано: %d", BATCH_LIMIT, input == null ? 0 : inputSize)
            );
        }

        Set<String> nonDuplicatedInput = input.stream()
            .map(BlackDictionaryInput::value)
            .collect(Collectors.toSet());
        Set<String> duplicatedSet = blackRepository.findDuplicates(nonDuplicatedInput);

        List<BlackDictionary> toSave = input.stream()
            .filter(i -> !duplicatedSet.contains(i.value()))
            .map(this::toEntity)
            .toList();

        int saved = toSave.isEmpty() ? 0 : blackRepository.saveAll(toSave).size();
        int duplicates = inputSize - saved;
        return new CreationOutput(inputSize, duplicates, saved);
    }

    protected BlackDictionary toEntity(BlackDictionaryInput input) {
        BlackDictionary entity = new BlackDictionary();
        entity.setBlockchain(input.blockchain());
        entity.setType(input.dictionaryType());
        entity.setValue(input.value());
        entity.setReason(input.reason());
        return entity;
    }
}
