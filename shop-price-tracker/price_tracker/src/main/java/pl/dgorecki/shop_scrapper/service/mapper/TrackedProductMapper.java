package pl.dgorecki.shop_scrapper.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dgorecki.shop_scrapper.entity.TrackedProduct;
import pl.dgorecki.shop_scrapper.service.dto.TrackedProductDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = ShopMapper.class)
public interface TrackedProductMapper {

    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "shop.shopName", target = "shopName")
    TrackedProductDTO toDto(TrackedProduct trackedProduct);
    @Mapping(source = "shopId", target = "shop")
    TrackedProduct toEntity(TrackedProductDTO trackedProductDTO);

    List<TrackedProductDTO> toDto(List<TrackedProduct> trackedProducts);

    List<TrackedProduct> toEntity(List<TrackedProductDTO> trackedProductDTOS);

    default TrackedProduct map(Long id) {
        if (id == null) {
            return null;
        }
        TrackedProduct trackedProduct = new TrackedProduct();
        trackedProduct.setId(id);
        return trackedProduct;
    }
}
