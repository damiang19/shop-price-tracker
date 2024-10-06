package pl.dgorecki.scrapper.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.dgorecki.scrapper.service.errors.PatternNotFoundException;
import pl.dgorecki.scrapper.utils.RegexMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapperServiceImpl implements ScrapperService {

    private final ShopService shopService;
    private final UrlValidatorService urlValidatorService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final static Pattern pricePattern = Pattern.compile(UrlRegexp.PRICE.getValue());


    @Override
    public ScrappedProductDataDTO scrapActualProductPrice(String url) {
        String linkToProduct = urlValidatorService.validateUrlFormat(url);
        ShopDTO shopDTO = shopService.getByUrl(linkToProduct);
        return downloadInformationAboutProduct(linkToProduct, shopDTO);
    }

    private ScrappedProductDataDTO downloadInformationAboutProduct(String linkToProduct, ShopDTO shopDTO) {
        String websiteContent = fetchWebsiteContent(linkToProduct);
        JsonNode jsonNode = convertToJsonNode(websiteContent, shopDTO);
        return assignProductData(jsonNode, shopDTO, linkToProduct);
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

    private JsonNode convertToJsonNode(String htmlCode, ShopDTO shopDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = findProductJson(htmlCode, shopDTO);
        JSONObject jsonObject = new JSONObject(jsonString.replaceAll("\n", "").replaceAll(" ", "").replaceAll(",}", "}"));
        try {
            return objectMapper.readTree(jsonObject.toString());
        } catch (JsonProcessingException jsonMappingException) {
            throw new RuntimeException("Invalid json format"); // TODO : obsluga bledow (exception handler)
        }
    }

    private String findProductJson(String htmlCode, ShopDTO shopDTO) {
        return urlValidatorService.extractJson(htmlCode).stream()
                .filter(json -> json.contains(shopDTO.getPriceHtmlClass()) && json.contains(shopDTO.getProductNameHtmlClass())).findFirst().orElse(""); // throw jesli pusty
    }

    private ScrappedProductDataDTO assignProductData(JsonNode jsonNode, ShopDTO shopDTO, String linkToProduct) {
        ScrappedProductDataDTO scrappedProductDataDTO = new ScrappedProductDataDTO();
        scrappedProductDataDTO.setProductName(jsonNode.get(shopDTO.getProductNameHtmlClass()).asText());
        BigDecimal price = convertPriceToBigDecimal(jsonNode.get(shopDTO.getProductNameHtmlClass()).asText());
        scrappedProductDataDTO.setShopName(shopDTO.getName());
        scrappedProductDataDTO.setPrice(price);
        scrappedProductDataDTO.setUrl(linkToProduct);
        return scrappedProductDataDTO;
    }


    private BigDecimal convertPriceToBigDecimal(String price) {
        return new BigDecimal(RegexMatcher.filter(price, pricePattern)
                .orElseThrow(() -> new PatternNotFoundException("Cannot convert price to BigDecimal. Invalid String format.")));


    }

}
