package io.ivanbyone.dictionary_service.model.errors;

public class InvalidInputException extends RuntimeException {
    
    public InvalidInputException(String msg) {
        super(msg);
    }
}
