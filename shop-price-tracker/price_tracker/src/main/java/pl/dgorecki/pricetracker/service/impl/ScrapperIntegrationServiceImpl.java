package pl.dgorecki.pricetracker.service.impl;


import org.springframework.stereotype.Service;
import pl.dgorecki.pricetracker.ScrapperIntegrationService;
import pl.dgorecki.pricetracker.service.dto.ScrappedProductData;
import pl.dgorecki.pricetracker.service.dto.ShopDTO;

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
