package pl.dgorecki.shop_scrapper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dgorecki.shop_scrapper.controller.payload.ShopData;
import pl.dgorecki.shop_scrapper.service.ShopService;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/shop")
    public ResponseEntity<ShopDTO> addNewShop(@RequestBody @Valid ShopData shopData) {
        log.debug("REST request to save new shop : {}", shopData);
        ShopDTO persistedShop = shopService.save(shopData);
        return ResponseEntity.status(HttpStatus.CREATED).body(persistedShop);
    }
}
