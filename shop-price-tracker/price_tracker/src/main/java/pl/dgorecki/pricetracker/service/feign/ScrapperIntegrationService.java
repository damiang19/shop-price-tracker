package pl.dgorecki.pricetracker.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dgorecki.pricetracker.service.dto.ScrappedProductData;

import java.util.List;

@FeignClient(name = "scrapper", fallback = ScrapperFallback.class)
public interface ScrapperIntegrationService {

    @GetMapping(value = "/scrap-product-price", consumes = "application/json")
    ResponseEntity<ScrappedProductData> scrapProductData(@RequestParam("url") String url);

}
