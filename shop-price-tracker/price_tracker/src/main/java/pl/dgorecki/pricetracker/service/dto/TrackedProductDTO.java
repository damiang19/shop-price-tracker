package pl.dgorecki.pricetracker.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrackedProductDTO {

    private Long id;

    private String url;

    private BigDecimal price;

    private String productName;

    private String shopName;

    private Instant created;

    private List<TrackedProductArchiveDTO> archives;

    public static void updateByActualPrice(TrackedProductDTO trackedProductDTO, ScrappedProductData scrappedProductData) {
        trackedProductDTO.setPrice(scrappedProductData.getPrice());
        trackedProductDTO.setCreated(Instant.now());
    }
}
