package pl.dgorecki.pricetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dgorecki.pricetracker.entity.TrackedProductArchive;

import java.util.List;

@Repository
public interface TrackedProductArchiveRepository extends JpaRepository<TrackedProductArchive, Long> {

    List<TrackedProductArchive> findAllByTrackedProductId(Long trackedProductId);
}
