package org.example.walletflow.exception;

import org.example.walletflow.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WalletExceptionHandler {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<ErrorResponse> handleWalletException(WalletException ex) {
        // Получаем статус из аннотации @ResponseStatus
        ResponseStatus statusAnnotation = ex.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = statusAnnotation != null ?
                statusAnnotation.value() :
                HttpStatus.INTERNAL_SERVER_ERROR;

        // Формируем ответ
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(ex.getErrorCode(), ex.getMessage()));
    }
}
