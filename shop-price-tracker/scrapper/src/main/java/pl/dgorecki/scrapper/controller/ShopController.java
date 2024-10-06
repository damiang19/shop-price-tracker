package pl.dgorecki.scrapper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dgorecki.scrapper.controller.payload.ShopData;
import pl.dgorecki.scrapper.service.ShopService;
import pl.dgorecki.scrapper.service.dto.ShopDTO;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/shops")
    public ResponseEntity<ShopDTO> addNewShop(@RequestBody @Valid ShopData shopData) {
        log.debug("REST request to save new shop : {}", shopData);
        ShopDTO persistedShop = shopService.save(shopData);
        return ResponseEntity.status(HttpStatus.CREATED).body(persistedShop);
    }

    @GetMapping("/shops")
    public ResponseEntity<List<ShopDTO>> getShopsByNames(@RequestParam List<String> names) {
        log.debug("REST request to get all shops with names : {}", names);
        List<ShopDTO> shops = shopService.getAllByNames(names);
        return ResponseEntity.status(HttpStatus.CREATED).body(shops);
    }
}
