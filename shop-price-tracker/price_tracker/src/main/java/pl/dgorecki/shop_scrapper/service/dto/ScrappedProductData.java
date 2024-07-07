package pl.dgorecki.shop_scrapper.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ScrappedProductData {
    private String productName;
    private BigDecimal price;
}
