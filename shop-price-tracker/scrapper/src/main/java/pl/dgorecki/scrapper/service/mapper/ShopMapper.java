package pl.dgorecki.scrapper.service.mapper;

import org.mapstruct.Mapper;
import pl.dgorecki.scrapper.entity.Shop;
import pl.dgorecki.scrapper.service.dto.ShopDTO;

import java.util.List;

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
