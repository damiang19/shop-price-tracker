package pl.dgorecki.shop_scrapper.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.dgorecki.shop_scrapper.ShopScrapperApplication;
import pl.dgorecki.shop_scrapper.service.errors.InvalidUrlException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest(classes = ShopScrapperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlValidatorServiceTest {
    @Autowired
    private UrlValidatorService urlValidatorService;

    @Test
    void shouldThrowException() {
        Exception exception = assertThrows(InvalidUrlException.class, () -> {
            urlValidatorService.getBaseShopUrl("fdfdfdfdf");
        });
        assertThat(exception.getMessage()).isEqualTo("URL is not correctly formatted.");
    }

    @Test
    void shouldExtractShopUrl() {
        String url = urlValidatorService.getBaseShopUrl("https://www.morele.net/plyta-glowna-asrock-b760-pro-rs-12599415/");
        assertThat(url).isEqualTo("https://www.morele.net");
    }

    @Test
    void shouldExtractLinkToProduct() {
        String url = urlValidatorService.validateUrlFormat("testwebsitestringhttps://www.morele.net/plyta-glowna-asrock-b760-pro-rs-12599415/");
        assertThat(url).isEqualTo("https://www.morele.net/plyta-glowna-asrock-b760-pro-rs-12599415/");
    }
}


