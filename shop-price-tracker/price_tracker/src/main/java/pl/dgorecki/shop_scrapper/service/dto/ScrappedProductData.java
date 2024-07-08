package pl.dgorecki.shop_scrapper.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ScrappedProductData {
    private String productName;
    private String shopName;
    private BigDecimal price;
    private String url;
}
