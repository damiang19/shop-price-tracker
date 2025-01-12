package pl.dgorecki.scrapper.service.errors;

public class ProductJsonNotFoundException extends RuntimeException{
    public ProductJsonNotFoundException(String message) {
        super(message);
    }
}
