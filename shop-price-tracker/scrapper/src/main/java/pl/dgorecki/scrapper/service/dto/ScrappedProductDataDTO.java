package pl.dgorecki.scrapper.service.dto;

import lombok.*;

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

    public ScrappedProductDataDTO(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
}
