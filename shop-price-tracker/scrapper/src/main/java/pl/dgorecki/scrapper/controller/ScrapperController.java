package pl.dgorecki.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(ScrapperController.class);


    @GetMapping("/scrap-product-price")
    public ResponseEntity<ScrappedProductDataDTO> scrapProductData(@RequestParam String url) {
        log.info("REST request to scrap product with url {}", url);
        ScrappedProductDataDTO scrappedProductDataDTO = scrapperService.scrapActualProductPrice(url);
        return ResponseEntity.status(HttpStatus.OK).body(scrappedProductDataDTO);
    }

    @GetMapping("/scrap-product-prices")
    public ResponseEntity<List<ScrappedProductDataDTO>> scrapListOfProducts(@RequestParam List<String> urls) {
        return null;
    }
}
