package pl.dgorecki.shop_scrapper;

import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;

import java.util.List;

public interface ScrapperIntegrationService {

    ScrappedProductData scrapProductData(String rl);
    List<ShopDTO> getAllShops(List<String> shopNames);
}
