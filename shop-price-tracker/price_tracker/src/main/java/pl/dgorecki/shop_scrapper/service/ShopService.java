package pl.dgorecki.shop_scrapper.service;

import pl.dgorecki.shop_scrapper.controller.payload.ShopData;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;

import java.util.List;

public interface ShopService {
    ShopDTO save(ShopData shopData);

    ShopDTO getByUrl(String url);

    List<ShopDTO> getAllByIds(List<Long> shopIds);

}
