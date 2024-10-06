package pl.dgorecki.scrapper.service.errors;

public class PatternNotFoundException extends RuntimeException{
    public PatternNotFoundException(String message) {
        super(message);
    }
}
