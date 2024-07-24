package pl.dgorecki.shop_scrapper.service.impl;


import org.springframework.stereotype.Service;
import pl.dgorecki.shop_scrapper.ScrapperIntegrationService;
import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;

import java.util.List;

@Service
public class ScrapperIntegrationServiceImpl implements ScrapperIntegrationService {
    @Override
    public ScrappedProductData scrapProductData(String rl) {
        return null;
    }

    @Override
    public List<ShopDTO> getAllShops(List<String> shopNames) {
        return null;
    }
}
