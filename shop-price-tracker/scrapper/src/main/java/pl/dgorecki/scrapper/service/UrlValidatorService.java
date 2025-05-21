package pl.dgorecki.scrapper.service;

import pl.dgorecki.scrapper.enums.JsonRegex;

import java.util.List;

public interface UrlValidatorService {

    String validateUrlFormat(String url);

    String getBaseShopUrl(String url);

    List<String> extractJson(String page, JsonRegex jsonRegex);
}

