package pl.dgorecki.shop_scrapper.service;

import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.TrackedProductDTO;

import java.util.List;

public interface TrackedProductService {
    TrackedProductDTO save(ScrappedProductData scrappedData);

    TrackedProductDTO addNewProduct(String url);

    void updateProductsByActualPrices(List<TrackedProductDTO> trackedProductDTOList);

}
