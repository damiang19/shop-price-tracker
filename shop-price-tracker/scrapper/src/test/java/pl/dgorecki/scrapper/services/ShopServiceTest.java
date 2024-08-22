package pl.dgorecki.scrapper.services;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.dgorecki.scrapper.ScrapperApplication;
import pl.dgorecki.scrapper.controller.payload.ShopData;
import pl.dgorecki.scrapper.entity.Shop;
import pl.dgorecki.scrapper.repository.ShopRepository;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.service.impl.ShopServiceImpl;
import pl.dgorecki.scrapper.service.impl.UrlValidatorServiceImpl;
import pl.dgorecki.scrapper.service.mapper.ShopMapper;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ScrapperApplication.class)
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
        when(urlValidatorService.getBaseShopUrl(anyString())).thenReturn("http://");
        when(shopMapper.toDto(any(Shop.class))).thenReturn(new ShopDTO());
        when(shopRepository.save(any(Shop.class))).thenReturn(new Shop());
        ShopDTO persist = shopService.save(new ShopData("tt", "tt", "tt", "tt"));
    }
}
