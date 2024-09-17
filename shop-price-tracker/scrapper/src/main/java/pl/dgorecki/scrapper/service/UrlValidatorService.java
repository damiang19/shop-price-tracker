package pl.dgorecki.scrapper.service;

import java.util.List;

public interface UrlValidatorService {

    String validateUrlFormat(String url);

    String getBaseShopUrl(String url);

    List<String> extractJson(String page);
}

