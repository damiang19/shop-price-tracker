package pl.dgorecki.pricetracker;

import pl.dgorecki.pricetracker.service.dto.ScrappedProductData;
import pl.dgorecki.pricetracker.service.dto.ShopDTO;

import java.util.List;

public interface ScrapperIntegrationService {

    ScrappedProductData scrapProductData(String rl);
    List<ShopDTO> getAllShops(List<String> shopNames);
}
