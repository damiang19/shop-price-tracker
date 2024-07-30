package pl.dgorecki.pricetracker.service;

import pl.dgorecki.pricetracker.service.dto.ScrappedProductData;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;

import java.util.List;

public interface TrackedProductService {
    TrackedProductDTO save(ScrappedProductData scrappedData);

    TrackedProductDTO addNewProduct(String url);

    void updateProductsByActualPrices(List<TrackedProductDTO> trackedProductDTOList);

}
