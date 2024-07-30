package pl.dgorecki.pricetracker.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dgorecki.pricetracker.entity.TrackedProductArchive;
import pl.dgorecki.pricetracker.repository.TrackedProductArchiveRepository;
import pl.dgorecki.pricetracker.service.TrackedProductArchiveService;
import pl.dgorecki.pricetracker.service.dto.TrackedProductArchiveDTO;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;
import pl.dgorecki.pricetracker.service.mapper.TrackedProductArchiveMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TrackedProductArchiveServiceImpl implements TrackedProductArchiveService {

    private final TrackedProductArchiveRepository trackedProductArchiveRepository;
    private final TrackedProductArchiveMapper trackedProductArchiveMapper;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public List<TrackedProductArchiveDTO> saveAll(List<TrackedProductDTO> trackedProductDTOList) {
        log.debug("Request to create archived tracked products");
        List<TrackedProductArchiveDTO> trackedProductArchiveDTOList = new ArrayList<>();
        trackedProductDTOList.forEach(trackedProductDTO -> trackedProductArchiveDTOList.add(TrackedProductArchiveDTO.from(trackedProductDTO)));
        List<TrackedProductArchive> listOfTrackedProductsArchives = trackedProductArchiveRepository
                .saveAll(trackedProductArchiveMapper.toEntity(trackedProductArchiveDTOList));
        return trackedProductArchiveMapper.toDto(listOfTrackedProductsArchives);
    }

    @Override
    @Transactional
    public List<TrackedProductArchiveDTO> getAllByTrackedProductId(Long id) {
        log.debug("Request to get all archives for tracked product with id : " + id);
        List<TrackedProductArchive> trackedProductArchives = trackedProductArchiveRepository.findAllByTrackedProductId(id);
        return trackedProductArchiveMapper.toDto(trackedProductArchives);
    }
    @Override
    @Transactional
    public void setArchivesForAllTrackedProducts(List<TrackedProductDTO> trackedProductDTOList) {
        List<TrackedProductArchiveDTO> listOfArchives;
        for (TrackedProductDTO trackedProductDTO : trackedProductDTOList) {
            listOfArchives = getAllByTrackedProductId(trackedProductDTO.getId());
            trackedProductDTO.setArchives(listOfArchives);
        }
    }
}
