package pl.dgorecki.scrapper.controller.error;

public record ErrorResponse(String message, int status, String errorCode) {
}
