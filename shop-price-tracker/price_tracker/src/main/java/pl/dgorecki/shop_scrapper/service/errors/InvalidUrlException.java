package pl.dgorecki.shop_scrapper.service.errors;

public class InvalidUrlException extends RuntimeException{
    public InvalidUrlException(String message) {
        super(message);
    }
}
