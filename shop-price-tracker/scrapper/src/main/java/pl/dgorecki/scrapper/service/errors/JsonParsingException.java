package pl.dgorecki.scrapper.service.errors;

public class JsonParsingException extends RuntimeException{
    public JsonParsingException(String message) {
        super(message);
    }
}