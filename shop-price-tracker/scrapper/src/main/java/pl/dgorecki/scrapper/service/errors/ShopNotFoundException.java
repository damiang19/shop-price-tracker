package pl.dgorecki.scrapper.service.errors;

public class ShopNotFoundException extends RuntimeException{
    public ShopNotFoundException(String message) {
        super(message);
    }
}
