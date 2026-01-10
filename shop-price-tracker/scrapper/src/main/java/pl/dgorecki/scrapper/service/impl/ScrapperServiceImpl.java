package pl.dgorecki.scrapper.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.dgorecki.scrapper.service.CurlService;
import pl.dgorecki.scrapper.service.ScrapperService;
import pl.dgorecki.scrapper.service.ShopService;
import pl.dgorecki.scrapper.service.UrlValidatorService;
import pl.dgorecki.scrapper.service.dto.ScrappedProductDataDTO;
import pl.dgorecki.scrapper.service.dto.ShopDTO;
import pl.dgorecki.scrapper.service.errors.JsonParsingException;
import pl.dgorecki.scrapper.service.errors.ProductJsonNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class ScrapperServiceImpl implements ScrapperService {

    private final ShopService shopService;
    private final CurlService curlService;
    private final UrlValidatorService urlValidatorService;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public ScrappedProductDataDTO scrapActualProductPrice(String url) {
        String linkToProduct = urlValidatorService.validateUrlFormat(url);
        ShopDTO shopDTO = shopService.getByUrl(linkToProduct);
        return downloadInformationAboutProduct(linkToProduct, shopDTO);
    }

    @Override
    public List<ScrappedProductDataDTO> scrapListOfProducts(List<String> urls) {
        List<ScrappedProductDataDTO> listOfProducts = new ArrayList<>();
        for (String url : urls) {
            listOfProducts.add(scrapActualProductPrice(url));
        }
        return listOfProducts;
    }

    private ScrappedProductDataDTO downloadInformationAboutProduct(String linkToProduct, ShopDTO shopDTO) {
        String websiteContent = curlService.fetchWebsiteContent(linkToProduct);
        JsonNode jsonNode = convertToJsonNode(websiteContent, shopDTO);
        return ScrappedProductDataDTO.create(jsonNode, shopDTO, linkToProduct);
    }


    private JsonNode convertToJsonNode(String htmlCode, ShopDTO shopDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = findProductJson(htmlCode, shopDTO);
        JSONObject jsonObject = new JSONObject
                (jsonString.replaceAll("\n", "").replaceAll(",}", "}")
                        .replaceAll(";","").replaceAll("/", ""));
        try {
            return objectMapper.readTree(jsonObject.toString());
        } catch (JsonProcessingException jsonMappingException) {
            log.error("Error during converting jsonString : {}", jsonString);
            throw new JsonParsingException("Invalid JSON format");
        }
    }

    private String findProductJson(String htmlCode, ShopDTO shopDTO) {
        List<String> jsonValuePath = Stream.concat(Arrays.stream(shopDTO.getProductNameHtmlClass().split("\\.")),Arrays.stream(shopDTO.getPriceHtmlClass().split("\\."))).toList();
        List<Predicate<String>> allPredicates = new ArrayList<>();
        for (String s : jsonValuePath) {
            allPredicates.add(pr -> pr.contains(s));
        }
        return urlValidatorService.extractJsons(htmlCode, shopDTO.getJsonRegex())
                .stream()
                .filter(allPredicates.stream().reduce(x->true, Predicate::and))
                .findFirst()
                .orElseThrow(() -> new ProductJsonNotFoundException("JSON with the given product name and price was not found"));
    }

}
