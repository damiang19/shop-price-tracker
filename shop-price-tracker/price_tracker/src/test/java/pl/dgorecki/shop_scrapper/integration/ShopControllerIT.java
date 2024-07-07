package pl.dgorecki.shop_scrapper.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.dgorecki.shop_scrapper.ShopScrapperApplication;
import pl.dgorecki.shop_scrapper.controller.payload.ShopData;
import pl.dgorecki.shop_scrapper.entity.Shop;
import pl.dgorecki.shop_scrapper.repository.ShopRepository;
import static org.assertj.core.api.Assertions.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@AutoConfigureWebTestClient
@SpringBootTest(classes = ShopScrapperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ShopRepository shopRepository;


    @BeforeEach
    public void init() {
    }

    @Test
    void shouldCreateNewCustomers() {
        ShopData payload = new ShopData("name", "price", "morele.net", "morele.net");
        this.webTestClient
                .post()
                .uri("/shop")
                .bodyValue(payload)
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isCreated();
         Shop persistedShop =  shopRepository.findByShopUrl("morele.net").orElse(null);
         assertThat(persistedShop).isNotNull();

    }

}
