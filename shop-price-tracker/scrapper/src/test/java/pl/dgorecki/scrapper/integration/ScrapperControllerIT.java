package pl.dgorecki.scrapper.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.dgorecki.scrapper.ScrapperApplication;
import pl.dgorecki.scrapper.enums.JsonRegex;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.service.impl.CurlServiceImpl;
import pl.dgorecki.scrapper.service.impl.ShopServiceImpl;
import pl.dgorecki.scrapper.service.impl.UrlValidatorServiceImpl;

import java.math.BigDecimal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureWebTestClient(timeout = "10000")
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

    @Test
    void shouldFindProductData() {
        //GIVEN
        ShopDTO shopDTO = createExampleShop();
        shopDTO.setJsonRegex(JsonRegex.MEDIA_JSON);
        String payload = "http://www.example.org/super-pendrive-test";
        //WHEN
        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
        Mockito.when(curlService.fetchWebsiteContent(payload)).thenReturn("{\"price\":\"50\",\"name\":\"test\"}");
        ScrappedProductDataDTO scrappedProductDataDTO = this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
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
        shopDTO.setProductNameHtmlClass("offers.name");
        shopDTO.setJsonRegex(JsonRegex.INNER_JSON);
        String payload = "http://www.example.org/super-pendrive-test";
        //WHEN
        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
        Mockito.when(curlService.fetchWebsiteContent(payload)).thenReturn("{\"@context\":\"http://schema.org/\",\"@type\":\"Product\",\"name\":\"Lenovo IdeaPad Slim 3-15 i5-12450H/16GB/512/Win11\",\"productID\":\"1203206\",\"sku\":\"1203206\",\"mpn\":\"83ER0009PB\",\"offers\":{\"@type\":\"Offer\",\"priceCurrency\":\"PLN\",\"price\":2399,\"itemCondition\":\"http://schema.org/NewCondition\",\"name\":\"http://schema.org/InStock\"}}");
        ScrappedProductDataDTO scrappedProductDataDTO = this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
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
        assertThat(scrappedProductDataDTO.getProductName()).isEqualTo("http:schema.orgInStock");
        assertThat(scrappedProductDataDTO.getPrice()).isEqualTo(BigDecimal.valueOf(2399));
    }

    @Test
    void shouldThrowExceptionIfProductNameFieldDoesNotExistInJson() {
        //GIVEN
        ShopDTO shopDTO = createExampleShop();
        shopDTO.setProductNameHtmlClass("offers.name");
        shopDTO.setJsonRegex(JsonRegex.INNER_JSON);
        String payload = "http://www.example.org/super-pendrive-test";
        //WHEN
        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
        Mockito.when(curlService.fetchWebsiteContent(payload)).thenReturn("{\"@context\":\"http://schema.org/\",\"@type\":\"Product\",\"name\":\"Lenovo IdeaPad Slim 3-15 i5-12450H/16GB/512/Win11\",\"productID\":\"1203206\",\"sku\":\"1203206\",\"mpn\":\"83ER0009PB\",\"offers\":{\"@type\":\"Offer\",\"priceCurrency\":\"PLN\",\"price\":2399,\"itemCondition\":\"http://schema.org/NewCondition\"}}");
        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/scrap-product-price")
                        .queryParam("url", payload)
                        .build()
                )
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Product name or price field not found: offers.name");

    }

    @Test
    void shouldFindProductDataWhenItIsNestedXkom() {
        //GIVEN
        ShopDTO shopDTO = createExampleShop();
        shopDTO.setProductNameHtmlClass("name");
        shopDTO.setPriceHtmlClass("offers.price");
        shopDTO.setJsonRegex(JsonRegex.INNER_JSON);
        String payload = "http://www.example.org/super-pendrive-test";
        //WHEN
        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
        Mockito.when(curlService.fetchWebsiteContent(payload))
                .thenReturn("{\"@context\":\"http://schema.org/\",\"@type\":\"Product\",\"name\":\"HP Omen 16 i7-13620H/16GB/512/Win11 RTX4060 144Hz\",\"productID\":\"1301209\",\"sku\":\"1301209\",\"mpn\":\"16-wd0004nw (B4MC4EA)\",\"image\":[\"https://cdn.x-kom.pl/i/setup/images/prod/big/product-new-big,,2024/12/pr_2024_12_13_8_51_18_13_00.jpg\",\"https://cdn.x-kom.pl/i/setup/images/prod/big/product-new-big,,2024/12/pr_2024_12_13_8_51_19_685_01.jpg\",\"https://cdn.x-kom.pl/i/setup/images/prod/big/product-new-big,,2024/12/pr_2024_12_13_8_51_21_372_02.jpg\",\"https://cdn.x-kom.pl/i/setup/images/prod/big/product-new-big,,2024/12/pr_2024_12_13_8_51_23_122_03.jpg\",\"https://cdn.x-kom.pl/i/setup/images/prod/big/product-new-big,,2024/12/pr_2024_12_13_8_51_24_763_04.jpg\",\"https://cdn.x-kom.pl/i/setup/images/prod/big/product-new-big,,2024/12/pr_2024_12_13_8_51_26_357_05.jpg\"],\"offers\":{\"@type\":\"Offer\",\"priceCurrency\":\"PLN\",\"price\":4899,\"itemCondition\":\"http://schema.org/NewCondition\",\"availability\":\"http://schema.org/InStock\"},\"brand\":{\"@type\":\"Thing\",\"name\":\"HP\"}}");
        ScrappedProductDataDTO scrappedProductDataDTO = this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
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
        assertThat(scrappedProductDataDTO.getProductName()).isEqualTo("http:schema.orgInStock");
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
