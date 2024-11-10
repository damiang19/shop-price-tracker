package pl.dgorecki.scrapper.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dgorecki.scrapper.controller.payload.ShopData;
import pl.dgorecki.scrapper.entity.Shop;
import pl.dgorecki.scrapper.repository.ShopRepository;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.service.impl.ShopServiceImpl;
import pl.dgorecki.scrapper.service.impl.UrlValidatorServiceImpl;
import pl.dgorecki.scrapper.service.mapper.ShopMapper;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {
    @Mock
    private ShopRepository shopRepository;

    @Mock
    private UrlValidatorServiceImpl urlValidatorService;

    @Mock
    ShopMapper shopMapper;

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    void findAll_should_return_student_list() {
        Shop exampleShop = new Shop();
        exampleShop.setName("example");
        exampleShop.setShopUrl("www.example.org");
        exampleShop.setPriceHtmlClass("price");
        exampleShop.setProductNameHtmlClass("productName");

        ShopDTO shopDto = new ShopDTO();
        shopDto.setName("example");
        shopDto.setShopUrl("www.example.org");
        shopDto.setPriceHtmlClass("price");
        shopDto.setProductNameHtmlClass("productName");

        when(urlValidatorService.getBaseShopUrl("www.example.org")).thenReturn("www.er.org");
        when(shopRepository.save(any())).thenReturn(exampleShop);
        when(shopMapper.toDto(exampleShop)).thenReturn(shopDto);
        shopService.save(new ShopData("productName", "price", "example", "www.example.org"));

        Mockito.verify(this.urlValidatorService, Mockito.times(1))
                .getBaseShopUrl("www.example.org");
        Mockito.verify(this.shopRepository, Mockito.times(1))
                .save(any());
        Mockito.verify(this.shopMapper, Mockito.times(1))
                .toDto(exampleShop);

    }

    @Test
    void getShops() {

        when(shopRepository.findAllByNameIn(List.of("eo"))).thenReturn(List.of(new Shop()));
        List<ShopDTO> persist = shopService.getAllByNames(List.of("eo"));
        System.out.println(persist);


    }
}
