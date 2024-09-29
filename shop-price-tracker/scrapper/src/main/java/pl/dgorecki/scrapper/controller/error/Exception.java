package pl.dgorecki.scrapper.controller.error;

import org.springframework.http.HttpStatus;

public record Exception(String message, int status, String errorCode) {
}
