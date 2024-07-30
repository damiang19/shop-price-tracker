package pl.dgorecki.pricetracker.service.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackedProductCriteria {
    private String productNameStartsWith;
    private String createdLessThan;
}
