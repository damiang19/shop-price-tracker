package pl.dgorecki.pricetracker.integration;

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
import pl.dgorecki.pricetracker.PriceTrackerApplication;
import pl.dgorecki.pricetracker.service.feign.ScrapperIntegrationService;
import pl.dgorecki.pricetracker.service.mapper.TrackedProductMapper;


import java.math.BigDecimal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@AutoConfigureWebTestClient
@SpringBootTest(classes = PriceTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrackedProductControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ScrapperIntegrationService scrapperIntegrationService;

    @SpyBean
    private TrackedProductMapper trackedProductMapper;


    @BeforeEach
    public void init() {
    }
    // findValue w jsonNode znajduje pierwsza napotkana wartosc. Czasami pasuje aby przeszukalo na podstawie wskazanej informacji wartosc zagniezdzona
    //
//    @Test
//    void shouldFindProductData() {
//        //GIVEN
//        ShopDTO shopDTO = createExampleShop();
//        String payload = "http://www.example.org/super-pendrive-test";
//        //WHEN
//        Mockito.when(urlValidatorService.validateUrlFormat(payload)).thenReturn(payload);
//        Mockito.when(shopService.getByUrl(payload)).thenReturn(shopDTO);
//        Mockito.when(curlService.fetchWebsiteContent(payload)).thenReturn("{\"price\":\"50\",\"name\":\"test\"}");
//
//        this.webTestClient
//                .post()
//                .uri("/shops")
//                .bodyValue(payload)
//                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
//                .exchange()
//                .expectStatus()
//                .isCreated();
//        //THEN
//        assertThat(scrappedProductDataDTO.getProductName()).isEqualTo("test");
//        assertThat(scrappedProductDataDTO.getPrice()).isEqualTo(BigDecimal.valueOf(50));
//    }

}
