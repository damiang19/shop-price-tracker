package pl.dgorecki.shop_scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dgorecki.shop_scrapper.entity.TrackedProductArchive;

import java.util.List;

@Repository
public interface TrackedProductArchiveRepository extends JpaRepository<TrackedProductArchive, Long> {

    List<TrackedProductArchive> findAllByTrackedProductId(Long trackedProductId);
}
