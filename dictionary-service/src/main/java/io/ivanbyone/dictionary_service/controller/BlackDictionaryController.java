package io.ivanbyone.dictionary_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.ivanbyone.dictionary_service.model.CreationOutput;
import io.ivanbyone.dictionary_service.model.SuccessResponse;
import io.ivanbyone.dictionary_service.model.dictionary.BlackDictionaryRequest;
import io.ivanbyone.dictionary_service.service.DictionaryService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/v1/dictionary/black")
public class BlackDictionaryController {
    private final DictionaryService dictionaryService;
    
    @PostMapping
    public SuccessResponse<CreationOutput> create(@RequestBody BlackDictionaryRequest input) {
        CreationOutput output = dictionaryService.create(input.blackDictionaryInputs());
        return SuccessResponse.<CreationOutput>of(output);
    }
}
