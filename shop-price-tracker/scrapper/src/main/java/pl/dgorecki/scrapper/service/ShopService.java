package pl.dgorecki.scrapper.service;


import pl.dgorecki.scrapper.controller.payload.ShopData;
import pl.dgorecki.scrapper.service.dto.ShopDTO;

import java.util.List;

public interface ShopService {
    ShopDTO save(ShopData shopData);

    ShopDTO getByUrl(String url);

    List<ShopDTO> getAllByIds(List<Long> shopIds);

}
