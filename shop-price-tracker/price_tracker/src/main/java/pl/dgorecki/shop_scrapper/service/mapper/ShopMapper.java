package pl.dgorecki.shop_scrapper.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import pl.dgorecki.shop_scrapper.entity.Shop;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopDTO toDto(Shop shop);

    Shop toEntity(ShopDTO shopDTO);

    List<ShopDTO> toDto(List<Shop> shops);

    List<Shop> toEntity(List<ShopDTO> shopDTOList);

    default Shop map(Long id) {
        if (id == null) {
            return null;
        }
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }
}
