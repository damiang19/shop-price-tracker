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

    @Test
    void checkCurl() throws IOException {
        try {
            // Tworzenie polecenia cURL
            String[] command = {"curl", "-X", "GET", "https://www.morele.net/karta-graficzna-asus-geforce-gt-730-2gb-gddr5-gt730-4h-sl-2gd5-9387600/"};

            // Uruchomienie polecenia
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Odczyt danych wyjściowych
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            // Oczekiwanie na zakończenie procesu
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
            System.out.println("Response:");
           String s = urlValidatorService.extractJson(output.toString()).stream().filter(json -> json.contains("ProductName") && json.contains("Price")).findFirst().orElse("");
           s = s.replaceAll("\n", "").replaceAll(" ", "").replaceAll(",}", "}");
           JSONObject jsonObject = new JSONObject(s);
            ObjectMapper objectMapper = new ObjectMapper();


            JsonNode jsonNode = objectMapper.readTree(jsonObject.toString());
            System.out.println(jsonNode.get("ProductName").asText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


