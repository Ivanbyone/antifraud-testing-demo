package io.ivanbyone.dictionary_service.model;

public record SuccessResponse<T>(
    boolean success,
    String message,
    T data
) {
    
    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<T>(true, "ok", data);
    }
}
