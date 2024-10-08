package pl.dgorecki.pricetracker.service;

import pl.dgorecki.pricetracker.service.dto.TrackedProductArchiveDTO;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;

import java.util.List;

public interface TrackedProductArchiveService {

    List<TrackedProductArchiveDTO> saveAll(List<TrackedProductDTO> trackedProductDTOList);

    List<TrackedProductArchiveDTO> getAllByTrackedProductId(Long id);

    void setArchivesForAllTrackedProducts(List<TrackedProductDTO> trackedProductDTOList);
}
