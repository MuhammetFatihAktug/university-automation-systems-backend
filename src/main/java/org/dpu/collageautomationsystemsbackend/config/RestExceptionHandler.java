package org.dpu.collageautomationsystemsbackend.config;

import org.dpu.collageautomationsystemsbackend.dto.ErrorDto;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorDto(exception.getMessage()));
    }
}
