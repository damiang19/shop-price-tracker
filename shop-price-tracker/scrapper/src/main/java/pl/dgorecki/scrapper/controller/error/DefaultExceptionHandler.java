package pl.dgorecki.scrapper.controller.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import pl.dgorecki.scrapper.service.errors.PatternNotFoundException;


@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PatternNotFoundException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        request.getContextPath();
        return new ResponseEntity<>( new Exception(ex.getMessage(), 500, "PATTERN_PARSE_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
