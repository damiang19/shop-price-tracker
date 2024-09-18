package pl.dgorecki.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.dgorecki.scrapper.enums.UrlRegexp;
import pl.dgorecki.scrapper.service.ScrapperService;
import pl.dgorecki.scrapper.service.ShopService;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.utils.RegexMatcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapperServiceImpl implements ScrapperService {

    private final ShopService shopService;
    private final UrlValidatorService urlValidatorService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final static Pattern pattern = Pattern.compile(UrlRegexp.PRICE.getValue());


    @Override
    public ScrappedProductDataDTO scrapActualProductPrice(String url) {
        String linkToProduct = urlValidatorService.validateUrlFormat(url);
        ShopDTO shopDTO = shopService.getByUrl(linkToProduct);

        String document = fetchWebsiteContent(linkToProduct);
        return downloadProductInfo(document, shopDTO);
    }

    private ScrappedProductDataDTO downloadInformationAboutProduct(String linkToProduct, ShopDTO shopDTO) {
        String websiteContent = fetchWebsiteContent(linkToProduct);
        JSONObject productJson = convertToJson(linkToProduct, shopDTO);
        return null;
    }

    @Override
    public String fetchWebsiteContent(String url) {
        Process process;
        try {
            process = prepareProcess(url);
        } catch (IOException e) {
            throw new RuntimeException("error");
        }
        return readAllLinesWithStream(new BufferedReader(new InputStreamReader(process.getInputStream())));
    }

    private Process prepareProcess(String url) throws IOException {
        String[] command = {"curl", "-X", "GET", url};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        return processBuilder.start();
    }

    private String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private JSONObject convertToJson(String htmlCode, ShopDTO shopDTO) {
        String jsonString = findProductJson(htmlCode, shopDTO).replaceAll(",}", "}");
        return new JSONObject(jsonString);
    }

    private String findProductJson(String htmlCode, ShopDTO shopDTO) {
        return urlValidatorService.extractJson(htmlCode).stream()
                .filter(json -> json.contains(shopDTO.getPriceHtmlClass()) && json.contains(shopDTO.getProductNameHtmlClass())).findFirst().orElse("");
    }

//    private ScrappedProductDataDTO assignProductData(JSONObject jsonObject, ShopDTO shopDTO) {
//        ScrappedProductDataDTO scrappedProductDataDTO = new ScrappedProductDataDTO();
//
//        scrappedProductDataDTO.setProductName();
//    }



    @Override
    public ScrappedProductDataDTO downloadProductInfo(String loadedPage, ShopDTO shopDTO) {
        String productName = getProductName(loadedPage, shopDTO);
        String price = getProductPrice(loadedPage, shopDTO);
        price = price.replaceAll(" ", "");
        price = price.replaceAll(",", ".");
        BigDecimal productPrice = new BigDecimal(price);
        return new ScrappedProductDataDTO(productName, productPrice);
    }

    private String getProductPrice(String loadedPage, ShopDTO shopDTO) {
        return null;
//        return loadedPage
//                .getElementsByClass(shopDTO.getPriceHtmlClass())
//                .get(0).attributes().asList()
//                .stream().map(Attribute::getValue)
//                .filter(s -> s.matches(UrlRegexp.PRICE.getValue())).findFirst()
//                .orElse(RegexMatcher.filter(loadedPage.getElementsByClass(shopDTO.getPriceHtmlClass()).text(), pattern).orElse(""));
    }

    private String getProductName(String loadedPage, ShopDTO shopDTO) {
        return null;
//        String title = loadedPage.getElementsByClass(shopDTO.getProductNameHtmlClass()).text();
//        return title.isEmpty() ? loadedPage.getElementsByClass(shopDTO.getProductNameHtmlClass()).get(0).children().tagName("h2").get(0).text() : title;
    }
}
