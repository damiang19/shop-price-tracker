package pl.dgorecki.shop_scrapper.service.criteria;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TrackedProductCriteria {
    private String productNameStartsWith;
    private String createdLessThan;
}
