package pl.dgorecki.shop_scrapper.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dgorecki.shop_scrapper.entity.TrackedProduct;
import pl.dgorecki.shop_scrapper.repository.TrackedProductRepository;
import pl.dgorecki.shop_scrapper.service.*;
import pl.dgorecki.shop_scrapper.service.dto.ScrappedProductData;
import pl.dgorecki.shop_scrapper.service.dto.TrackedProductDTO;
import pl.dgorecki.shop_scrapper.service.mapper.TrackedProductMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrackedProductServiceImpl implements TrackedProductService {

    private final TrackedProductMapper trackedProductMapper;
    private final TrackedProductRepository trackedProductRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public TrackedProductDTO save(ScrappedProductData scrappedData) {
        TrackedProductDTO trackedProductDTO = new TrackedProductDTO();
        trackedProductDTO.setProductName(scrappedData.getProductName());
        trackedProductDTO.setPrice(scrappedData.getPrice());
        trackedProductDTO.setCreated(Instant.now());
        trackedProductDTO.setShopName(scrappedData.getShopName());
        trackedProductDTO.setUrl(scrappedData.getUrl());
        TrackedProduct trackedProduct = trackedProductRepository.save(trackedProductMapper.toEntity(trackedProductDTO));
        return trackedProductMapper.toDto(trackedProduct);
    }

    @Override
    @Transactional
    public TrackedProductDTO addNewProduct(String url) {
        ScrappedProductData scrappedProductData = new ScrappedProductData();
        return save(scrappedProductData);
    }

    @Transactional
    @Override
    public void updateProductsByActualPrices(List<TrackedProductDTO> trackedProductDTOList) {
        Set<Long> shopIds = trackedProductDTOList.stream().map(TrackedProductDTO::getShopId).collect(Collectors.toSet());
        List<ShopDTO> shopDTOList = shopService.getAllByIds(shopIds.stream().toList());
        Map<ShopDTO, List<TrackedProductDTO>> shopToTrackedProducts = new HashMap<>();
//        for (ShopDTO shopDTO : shopDTOList) {
//            shopToTrackedProducts.put
//                    (shopDTO, trackedProductDTOList
//                            .stream()
//                            .filter(product -> product.getShopId().equals(shopDTO.getId()))
//                            .collect(Collectors.toList()));
//        }
//        for (Map.Entry<ShopDTO, List<TrackedProductDTO>> entry : shopToTrackedProducts.entrySet()) {
//            ShopDTO shopDTO = entry.getKey();
//            List<TrackedProductDTO> trackedProductsByShop = entry.getValue();
//            trackedProductsByShop.forEach(product -> {
//                ScrappedProductData scrappedProductData = scrapperService.scrapActualProductPrice(shopDTO, product.getUrl());
//                TrackedProductDTO.updateByActualPrice(product, scrappedProductData);
//                trackedProductRepository.saveAll(trackedProductMapper.toEntity(trackedProductsByShop));
//            });
//        }
    }

}
