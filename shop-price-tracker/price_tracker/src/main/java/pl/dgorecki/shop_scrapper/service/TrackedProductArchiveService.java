package pl.dgorecki.shop_scrapper.service;

import pl.dgorecki.shop_scrapper.service.dto.TrackedProductArchiveDTO;
import pl.dgorecki.shop_scrapper.service.dto.TrackedProductDTO;

import java.util.List;

public interface TrackedProductArchiveService {

    List<TrackedProductArchiveDTO> saveAll(List<TrackedProductDTO> trackedProductDTOList);

    List<TrackedProductArchiveDTO> getAllByTrackedProductId(Long id);

    void setArchivesForAllTrackedProducts(List<TrackedProductDTO> trackedProductDTOList);
}
