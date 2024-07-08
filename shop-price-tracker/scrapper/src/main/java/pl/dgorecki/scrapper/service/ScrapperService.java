package pl.dgorecki.scrapper.service;


import org.jsoup.nodes.Document;
import pl.dgorecki.scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.scrapper.service.dto.ShopDTO;

public interface ScrapperService {

    Document connectToTrackedProductSite(String url);

    ScrappedProductData scrapActualProductPrice(ShopDTO shopDTO, String url);

    ScrappedProductData downloadProductInfo(Document loadedPage, ShopDTO shopDTO);

}
