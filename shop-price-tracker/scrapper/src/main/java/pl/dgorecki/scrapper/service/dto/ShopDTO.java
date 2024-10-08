package pl.dgorecki.scrapper.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ShopDTO {
    private Long id;

    private String name;

    private String productNameHtmlClass;

    private String priceHtmlClass;

    private String shopUrl;

}
