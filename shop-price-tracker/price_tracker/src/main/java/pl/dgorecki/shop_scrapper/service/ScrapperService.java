package pl.dgorecki.shop_scrapper.service;

import org.jsoup.nodes.Document;
import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;


public interface ScrapperService {

    Document connectToTrackedProductSite(String url);

    ScrappedProductData scrapActualProductPrice(ShopDTO shopDTO, String url);

    ScrappedProductData downloadProductInfo(Document loadedPage, ShopDTO shopDTO);

}
