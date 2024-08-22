package pl.dgorecki.pricetracker.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.dgorecki.pricetracker.entity.TrackedProductArchive;
import pl.dgorecki.pricetracker.service.dto.TrackedProductArchiveDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = TrackedProductMapper.class)
public interface TrackedProductArchiveMapper {
    @Mapping(source = "trackedProduct.id", target = "trackedProductId")
    TrackedProductArchiveDTO toDto(TrackedProductArchive trackedProductArchive);

    @Mapping(source = "trackedProductId", target = "trackedProduct.id")
    TrackedProductArchive toEntity(TrackedProductArchiveDTO trackedProductArchiveDTO);

    List<TrackedProductArchiveDTO> toDto(List<TrackedProductArchive> trackedProductArchives);

    List<TrackedProductArchive> toEntity(List<TrackedProductArchiveDTO> trackedProductArchiveDTOS);

    default TrackedProductArchive map(Long id) {
        if (id == null) {
            return null;
        }
        TrackedProductArchive trackedProductArchive = new TrackedProductArchive();
        trackedProductArchive.setId(id);
        return trackedProductArchive;
    }
}
