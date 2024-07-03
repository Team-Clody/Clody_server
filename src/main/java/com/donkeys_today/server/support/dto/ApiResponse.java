package com.donkeys_today.server.support.dto;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.dto.type.SuccessType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Optional;

@JsonPropertyOrder({"status","message","data"})
public record ApiResponse<T>(
    int status,
    String message,
    Optional<T> data
) {
    public static <T> ApiResponse<T> success(SuccessType successType){
      return new ApiResponse<>(successType.getStatus(), successType.getMessage(),Optional.empty());
    }

    public static <T> ApiResponse<T> success(SuccessType successType, T data){
      return new ApiResponse<>(successType.getStatus(), successType.getMessage(), Optional.of(data));
    }

    public static <T> ApiResponse<T> error(ErrorType errorType){
      return new ApiResponse<>(errorType.getStatus(), errorType.getMessage(),null);
    }

    public static <T> ApiResponse<T> error(ErrorType errorType, T data){
      return new ApiResponse<>(errorType.getStatus(), errorType.getMessage(), Optional.of(data));
    }

    public static <T> ApiResponse<Exception> error(ErrorType errorType, Exception e){
      return new ApiResponse<>(errorType.getStatus(), errorType.getMessage(), Optional.of(e));
    }
}
