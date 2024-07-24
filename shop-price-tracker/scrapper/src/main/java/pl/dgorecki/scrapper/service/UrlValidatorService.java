package pl.dgorecki.scrapper.service;

public interface UrlValidatorService {

    String validateUrlFormat(String url);

    String getBaseShopUrl(String url);
}

