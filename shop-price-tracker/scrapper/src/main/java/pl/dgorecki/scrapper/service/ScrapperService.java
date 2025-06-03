package pl.dgorecki.scrapper.service;


import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;

import java.util.List;

public interface ScrapperService {

    ScrappedProductDataDTO scrapActualProductPrice(String url);
    List<ScrappedProductDataDTO> scrapListOfProducts(List<String> urls);
}
