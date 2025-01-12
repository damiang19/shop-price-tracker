package pl.dgorecki.scrapper.service.errors;

public class WebsiteProcessingException extends RuntimeException{
    public WebsiteProcessingException(String message) {
        super(message);
    }

}
