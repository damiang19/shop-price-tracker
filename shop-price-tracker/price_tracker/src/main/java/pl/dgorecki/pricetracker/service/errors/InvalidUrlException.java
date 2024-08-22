package pl.dgorecki.pricetracker.service.errors;

public class InvalidUrlException extends RuntimeException{
    public InvalidUrlException(String message) {
        super(message);
    }
}
