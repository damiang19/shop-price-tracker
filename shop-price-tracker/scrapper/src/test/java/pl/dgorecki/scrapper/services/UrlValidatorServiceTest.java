package pl.dgorecki.scrapper.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.dgorecki.scrapper.ScrapperApplication;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.errors.PatternNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = ScrapperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlValidatorServiceTest {
    @Autowired
    private UrlValidatorService urlValidatorService;

    @Test
    void shouldThrowException() {
        Exception exception = assertThrows(PatternNotFoundException.class, () -> {
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


