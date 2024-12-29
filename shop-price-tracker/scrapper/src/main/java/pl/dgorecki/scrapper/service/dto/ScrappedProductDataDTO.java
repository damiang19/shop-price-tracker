package pl.dgorecki.scrapper.service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import pl.dgorecki.scrapper.utils.BigDecimalConverter;

import java.math.BigDecimal;

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
        scrappedProductDataDTO.setProductName(jsonNode.get(shopDTO.getProductNameHtmlClass()).asText());
        BigDecimal price = BigDecimalConverter.fromString(jsonNode.findValue(shopDTO.getPriceHtmlClass()).asText());
        scrappedProductDataDTO.setShopName(shopDTO.getName());
        scrappedProductDataDTO.setPrice(price);
        scrappedProductDataDTO.setUrl(linkToProduct);
        return scrappedProductDataDTO;
    }
}
