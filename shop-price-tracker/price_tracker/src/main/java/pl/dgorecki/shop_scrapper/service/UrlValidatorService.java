package pl.dgorecki.shop_scrapper.service;

public interface UrlValidatorService {

    String validateUrlFormat(String url);

    String getBaseShopUrl(String url);
}
