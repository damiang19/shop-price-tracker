package pl.dgorecki.scrapper.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ScrappedProductData {
    private String productName;
    private String shopName;
    private BigDecimal price;
    private String url;
}
