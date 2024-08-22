package pl.dgorecki.pricetracker.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrackedProductArchiveDTO {

    private Long id;
    private Instant created;
    private BigDecimal price;
    private Long trackedProductId;


    public static TrackedProductArchiveDTO from(TrackedProductDTO trackedProductDTO) {
        TrackedProductArchiveDTO trackedProductArchiveDTO = new TrackedProductArchiveDTO();
        trackedProductArchiveDTO.setPrice(trackedProductDTO.getPrice());
        trackedProductArchiveDTO.setCreated(trackedProductDTO.getCreated());
        trackedProductArchiveDTO.setTrackedProductId(trackedProductDTO.getId());
        return trackedProductArchiveDTO;
    }

}
