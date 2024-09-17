package pl.dgorecki.scrapper.services;

import org.json.JSONObject;
import org.bouncycastle.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.dgorecki.scrapper.ScrapperApplication;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.errors.InvalidUrlException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = ScrapperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    void checkCurl() throws IOException {
//        byte[] array = new byte[24000];
//
//        String command =
//                "curl https://www.morele.net/karta-graficzna-asus-geforce-gt-730-2gb-gddr5-gt730-4h-sl-2gd5-9387600/ -A Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
//        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
//        Process process = processBuilder.start();
//        InputStream inputStream = process.getInputStream();
//        inputStream.read(array);
//        String data = new String(array);
//        System.out.println(data);
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
            String et = "{"
                    + "\"ProductName\": \"Karta graficzna Asus GeForce GT 730 2GB GDDR5 (GT730-4H-SL-2GD5)\","
                    + "\"ProductID\": \"9387600\","
                    + "\"Categories\": [\"Karty graficzne\"],"
                    + "\"ImageURL\": \"prodImg\","
                    + "\"URL\": \"https://www.morele.net/karta-graficzna-asus-geforce-gt-730-2gb-gddr5-gt730-4h-sl-2gd5-9387600/\","
                    + "\"Brand\": \"Asus\","
                    + "\"Price\": \"389.86\""
                    + "}";
           String s = urlValidatorService.extractJson(output.toString()).stream().filter(json -> json.contains("ProductName") && json.contains("Price")).findFirst().orElse("");
           s = s.replaceAll("\n","");
           s = s.replaceAll(" ", "");
           s = s.replaceAll(",}", "}");
           JSONObject jsonObject = new JSONObject(s);


            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


