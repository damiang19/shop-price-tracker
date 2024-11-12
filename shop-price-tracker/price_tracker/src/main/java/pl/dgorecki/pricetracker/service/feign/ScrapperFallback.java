package pl.dgorecki.pricetracker.service.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.dgorecki.pricetracker.service.dto.ScrappedProductData;
import pl.dgorecki.pricetracker.service.dto.ShopDTO;

import java.util.List;

@Component
public class ScrapperFallback implements ScrapperIntegrationService{
    @Override
    public ResponseEntity<ScrappedProductData> scrapProductData(String url) {
        return null;
    }

    @Override
    public ResponseEntity<List<ShopDTO>> getShopsByNames(List<String> names) {
        return null;
    }
}
