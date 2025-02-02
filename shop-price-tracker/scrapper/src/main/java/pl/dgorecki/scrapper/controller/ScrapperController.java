package pl.dgorecki.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dgorecki.scrapper.service.ScrapperService;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final ScrapperService scrapperService;

    @GetMapping("/scrap-product-price")
    public ResponseEntity<ScrappedProductDataDTO> scrapProductData(@RequestParam String url) {
        // TODO : zweryfikuj czy dany produkt nie jest juz  dodany do bazy danych
        ScrappedProductDataDTO scrappedProductDataDTO = scrapperService.scrapActualProductPrice(url);
        return ResponseEntity.status(HttpStatus.OK).body(scrappedProductDataDTO);
    }

    @GetMapping("/scrap-product-prices")
    public ResponseEntity<List<ScrappedProductDataDTO>> scrapListOfProducts(@RequestParam List<String> urls) {
        return null;
    }
}
