package pl.dgorecki.scrapper.service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import pl.dgorecki.scrapper.service.errors.ProductJsonNotFoundException;
import pl.dgorecki.scrapper.utils.BigDecimalConverter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ScrappedProductDataDTO {
    private String productName;
    private String shopName;
    private BigDecimal price;
    private String url;

    public ScrappedProductDataDTO(){}

    public static ScrappedProductDataDTO create(JsonNode jsonNode, ShopDTO shopDTO, String linkToProduct) {
        ScrappedProductDataDTO scrappedProductDataDTO = new ScrappedProductDataDTO();
        scrappedProductDataDTO.setProductName(valueFinder(shopDTO.getProductNameHtmlClass(),jsonNode).asText());
        BigDecimal price = BigDecimalConverter.fromString(valueFinder(shopDTO.getPriceHtmlClass(), jsonNode).asText());
        scrappedProductDataDTO.setShopName(shopDTO.getName());
        scrappedProductDataDTO.setPrice(price);
        scrappedProductDataDTO.setUrl(linkToProduct);
        return scrappedProductDataDTO;
    }

    private static JsonNode valueFinder(String value, JsonNode jsonNode) {
            List<String> jsonValuePath = Arrays.stream(value.split("\\.")).toList();
            for (String s : jsonValuePath) {
                jsonNode =  jsonNode.findValue(s);
            }
            if(jsonNode == null) {
                throw new ProductJsonNotFoundException("Product name or price field not found: " + value);
            } else {
                return jsonNode;
            }
    }

}
