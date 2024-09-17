package pl.dgorecki.scrapper.service;


import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;

public interface ScrapperService {

    String fetchWebsiteContent(String url);

    ScrappedProductDataDTO scrapActualProductPrice(String url);

    ScrappedProductDataDTO downloadProductInfo(String loadedPage, ShopDTO shopDTO);

}
