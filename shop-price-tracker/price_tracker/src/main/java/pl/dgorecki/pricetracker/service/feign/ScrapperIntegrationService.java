package pl.dgorecki.pricetracker.service.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dgorecki.pricetracker.controller.payload.ShopData;
import pl.dgorecki.pricetracker.service.dto.ScrappedProductData;
import pl.dgorecki.pricetracker.service.dto.ShopDTO;

import java.util.List;

@FeignClient
public interface ScrapperIntegrationService {


    @GetMapping("/scrap-product-price")
    ResponseEntity<ScrappedProductData> scrapProductData(@RequestParam String url);

    @GetMapping(value = "/shops", consumes = "application/json")
    ResponseEntity<List<ShopDTO>> getShopsByNames(@RequestParam List<String> names);
}