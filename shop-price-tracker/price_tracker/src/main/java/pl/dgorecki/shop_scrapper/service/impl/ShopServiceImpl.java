package pl.dgorecki.shop_scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dgorecki.shop_scrapper.controller.payload.ShopData;
import pl.dgorecki.shop_scrapper.entity.Shop;
import pl.dgorecki.shop_scrapper.repository.ShopRepository;
import pl.dgorecki.shop_scrapper.service.ShopService;
import pl.dgorecki.shop_scrapper.service.UrlValidatorService;
import pl.dgorecki.shop_scrapper.service.dto.ShopDTO;
import pl.dgorecki.shop_scrapper.service.errors.ShopNotFoundException;
import pl.dgorecki.shop_scrapper.service.mapper.ShopMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UrlValidatorService urlValidatorService;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    @Transactional
    public ShopDTO save(ShopData shopData) {
        Shop shop = new Shop();
        shop.setShopName(shopData.shopName());
        shop.setShopUrl(urlValidatorService.getBaseShopUrl(shopData.shopUrl()));
        shop.setPriceHtmlClass(shopData.priceHtmlClass());
        shop.setProductNameHtmlClass(shopData.productNameHtmlClass());
        return shopMapper.toDto(shopRepository.save(shop));
    }

    @Override
    @Transactional
    public ShopDTO getByUrl(String url) {
        String shopUrl = urlValidatorService.getBaseShopUrl(url);
        return shopMapper.toDto(shopRepository.findByShopUrl(shopUrl)
                .orElseThrow(() -> new ShopNotFoundException("Given shop does not exist in Database")));
    }
    @Override
    @Transactional
    public List<ShopDTO> getAllByIds(List<Long> shopIds) {
        return shopMapper.toDto(shopRepository.findAllById(shopIds));
    }
}
