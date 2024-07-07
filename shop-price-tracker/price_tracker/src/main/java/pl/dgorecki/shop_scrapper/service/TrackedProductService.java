package pl.dgorecki.shop_scrapper.service;

import org.springframework.data.domain.Pageable;
import pl.dgorecki.shop_scrapper.entity.Shop;
import pl.dgorecki.shop_scrapper.service.criteria.TrackedProductCriteria;
import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.TrackedProductDTO;

import java.util.List;

public interface TrackedProductService {
    TrackedProductDTO save(ScrappedProductData scrappedData, String url, Long shopId);

    TrackedProductDTO addNewProduct(String url);

    void updateProductsByActualPrices(List<TrackedProductDTO> trackedProductDTOList);

}
