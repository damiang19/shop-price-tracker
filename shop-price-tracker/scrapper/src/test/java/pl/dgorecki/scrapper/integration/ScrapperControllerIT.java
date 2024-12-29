package pl.dgorecki.scrapper.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.dgorecki.scrapper.ScrapperApplication;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.service.impl.CurlServiceImpl;
import pl.dgorecki.scrapper.service.impl.ShopServiceImpl;
import pl.dgorecki.scrapper.service.impl.UrlValidatorServiceImpl;

import java.math.BigDecimal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureWebTestClient
@SpringBootTest(classes = ScrapperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapperControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CurlServiceImpl curlService;

    @SpyBean
    private UrlValidatorServiceImpl urlValidatorService;

    @MockBean
    private ShopServiceImpl shopService;

    @BeforeEach
    public void init() {
    }
    // findValue w jsonNode znajduje pierwsza napotkana wartosc. Czasami pasuje aby przeszukalo na podstawie wskazanej informacji wartosc zagniezdzona
    //
    @Test
    void shouldFindProductData() {
        //GIVEN
        ShopDTO shopDTO = createExampleShop();
        String payload = "http://www.example.org/super-pendrive-test";
        //WHEN
        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
        Mockito.when(curlService.fetchWebsiteContent(payload)).thenReturn("{\"price\":\"50\",\"name\":\"test\"}");
        ScrappedProductDataDTO scrappedProductDataDTO = this.webTestClient
                .get()
                .uri( uriBuilder -> uriBuilder
                        .path("/scrap-product-price")
                        .queryParam("url", payload)
                        .build()
                )
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ScrappedProductDataDTO.class)
                .returnResult()
                .getResponseBody();
        //THEN
        assertThat(scrappedProductDataDTO.getProductName()).isEqualTo("test");
        assertThat(scrappedProductDataDTO.getPrice()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void shouldFindProductDataWhenItIsNested() {
        //GIVEN
        ShopDTO shopDTO = createExampleShop();
        String payload = "http://www.example.org/super-pendrive-test";
        //WHEN
        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
        Mockito.when(curlService.fetchWebsiteContent(payload)).thenReturn("{\"@context\":\"http://schema.org/\",\"@type\":\"Product\",\"name\":\"Lenovo IdeaPad Slim 3-15 i5-12450H/16GB/512/Win11\",\"productID\":\"1203206\",\"sku\":\"1203206\",\"mpn\":\"83ER0009PB\",\"offers\":{\"@type\":\"Offer\",\"priceCurrency\":\"PLN\",\"price\":2399,\"itemCondition\":\"http://schema.org/NewCondition\",\"name\":\"http://schema.org/InStock\"}}");
        ScrappedProductDataDTO scrappedProductDataDTO = this.webTestClient
                .get()
                .uri( uriBuilder -> uriBuilder
                        .path("/scrap-product-price")
                        .queryParam("url", payload)
                        .build()
                )
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ScrappedProductDataDTO.class)
                .returnResult()
                .getResponseBody();
        //THEN
        assertThat(scrappedProductDataDTO.getProductName()).isEqualTo("Lenovo IdeaPad Slim 3-15 i5-12450H/16GB/512/Win11");
        assertThat(scrappedProductDataDTO.getPrice()).isEqualTo(BigDecimal.valueOf(2399));
    }

    private ShopDTO createExampleShop() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopUrl("www.example.org");
        shopDTO.setName("example");
        shopDTO.setPriceHtmlClass("price");
        shopDTO.setProductNameHtmlClass("name");
        return shopDTO;
    }

}
