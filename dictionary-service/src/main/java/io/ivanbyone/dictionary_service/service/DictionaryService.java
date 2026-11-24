package io.ivanbyone.dictionary_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ivanbyone.dictionary_service.entity.BlackDictionary;
import io.ivanbyone.dictionary_service.entity.Outbox;
import io.ivanbyone.dictionary_service.entity.enums.OutboxStatus;
import io.ivanbyone.dictionary_service.model.CreationOutput;
import io.ivanbyone.dictionary_service.model.dictionary.BlackDictionaryInput;
import io.ivanbyone.dictionary_service.model.errors.InvalidInputException;
import io.ivanbyone.dictionary_service.repository.BlackDictionaryRepository;
import io.ivanbyone.dictionary_service.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectWriter;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private static final int BATCH_LIMIT = 10;

    private final BlackDictionaryRepository blackRepository;
    private final ObjectWriter jsonWriter;
    private final OutboxRepository outboxRepository;
    
    @Transactional
    public CreationOutput create(List<BlackDictionaryInput> input) {
        if (input == null) {
            throw new InvalidInputException("Список black значений не может быть null.");
        }

        int inputSize = input.size();
        if (input.isEmpty() || inputSize > BATCH_LIMIT) {
            throw new InvalidInputException(
                String.format("Список black значений должен содержать от 1 до %d значений. Передано: %d", BATCH_LIMIT, inputSize)
            );
        }

        Set<BlackDictionaryInput> nonDuplicatedInput = new HashSet<>(input);
        Set<String> nonDuplicatedInputValues = nonDuplicatedInput.stream()
            .map(BlackDictionaryInput::value)
            .collect(Collectors.toSet());
        Set<String> duplicatedSet = blackRepository.findDuplicates(nonDuplicatedInputValues);

        List<BlackDictionary> toSave = nonDuplicatedInput.stream()
            .filter(i -> !duplicatedSet.contains(i.value()))
            .map(this::toEntity)
            .toList();
        List<BlackDictionary> saved = toSave.isEmpty() ? List.of() : blackRepository.saveAll(toSave);

        int savedSize = saved.size();
        int duplicatedSize = inputSize - savedSize;

        List<Outbox> outboxToSave = saved.stream()
            .map(this::toOutbox)
            .toList();
        outboxRepository.saveAll(outboxToSave);

        return new CreationOutput(inputSize, duplicatedSize, savedSize);
    }

    protected BlackDictionary toEntity(BlackDictionaryInput input) {
        BlackDictionary entity = new BlackDictionary();
        entity.setBlockchain(input.blockchain());
        entity.setType(input.dictionaryType());
        entity.setValue(input.value());
        entity.setReason(input.reason());
        return entity;
    }

    protected Outbox toOutbox(BlackDictionary entity) {
        String msg = jsonWriter.writeValueAsString(entity);
        Outbox outbox = new Outbox();
        // TODO: занести в конфигурацию название очереди RabbitMQ
        outbox.setQueueName("");
        outbox.setMessage(msg);
        outbox.setStatus(OutboxStatus.WAIT);
        return outbox;
    }
}
