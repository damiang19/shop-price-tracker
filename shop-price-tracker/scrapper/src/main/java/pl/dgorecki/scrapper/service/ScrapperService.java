package pl.dgorecki.scrapper.service;


import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;

public interface ScrapperService {

    String fetchWebsiteContent(String url);

    ScrappedProductDataDTO scrapActualProductPrice(String url);

}
