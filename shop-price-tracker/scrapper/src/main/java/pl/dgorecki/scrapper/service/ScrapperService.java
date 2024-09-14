package pl.dgorecki.scrapper.service;


import org.jsoup.nodes.Document;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;

public interface ScrapperService {

    Document connectToTrackedProductSite(String url);

    ScrappedProductDataDTO scrapActualProductPrice(String url);

    ScrappedProductDataDTO downloadProductInfo(Document loadedPage, ShopDTO shopDTO);

}
