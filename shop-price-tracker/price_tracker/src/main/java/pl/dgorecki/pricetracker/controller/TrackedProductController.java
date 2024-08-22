package pl.dgorecki.pricetracker.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dgorecki.pricetracker.controller.payload.ShopUrl;
import pl.dgorecki.pricetracker.service.TrackedProductArchiveService;
import pl.dgorecki.pricetracker.service.TrackedProductQueryService;
import pl.dgorecki.pricetracker.service.TrackedProductService;
import pl.dgorecki.pricetracker.service.criteria.TrackedProductCriteria;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;

import java.util.List;


@AllArgsConstructor
@RestController
public class TrackedProductController {

    private final TrackedProductService trackedProductService;
    private final TrackedProductArchiveService trackedProductArchiveService;
    private final TrackedProductQueryService trackedProductQueryService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/tracked-product/save")
    public ResponseEntity<TrackedProductDTO> createTrackedProduct(@RequestBody @NonNull ShopUrl shopUrl) {
        log.debug("Request to create new TrackedProduct");
        TrackedProductDTO trackedProductDTO =  trackedProductService.addNewProduct(shopUrl.shopUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(trackedProductDTO);
    }

    @GetMapping("/tracked-product")
    public ResponseEntity<List<TrackedProductDTO>> findAll(TrackedProductCriteria trackedProductCriteria, Pageable pageable){
        log.debug("REST request to get all trackedProducts with criteria : {}", trackedProductCriteria);
        List<TrackedProductDTO> trackedProductDTOList = trackedProductQueryService.findByCriteria(trackedProductCriteria,pageable);
        trackedProductArchiveService.setArchivesForAllTrackedProducts(trackedProductDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body(trackedProductDTOList);
    }
}
