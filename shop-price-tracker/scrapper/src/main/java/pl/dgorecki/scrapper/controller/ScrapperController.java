package pl.dgorecki.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dgorecki.scrapper.service.ScrapperService;
import pl.dgorecki.scrapper.service.dto.ScrappedProductData;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final ScrapperService scrapperService;

    @GetMapping("/scrap-product-price")
    public ResponseEntity<ScrappedProductData> scrapProductData(@RequestParam String url) {
        ScrappedProductData scrappedProductData = scrapperService.scrapActualProductPrice(url);
        return ResponseEntity.status(HttpStatus.OK).body(scrappedProductData);
    }
}
