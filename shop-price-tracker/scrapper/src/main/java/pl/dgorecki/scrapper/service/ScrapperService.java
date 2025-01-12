package pl.dgorecki.scrapper.service;


import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;

public interface ScrapperService {

    ScrappedProductDataDTO scrapActualProductPrice(String url);

}
