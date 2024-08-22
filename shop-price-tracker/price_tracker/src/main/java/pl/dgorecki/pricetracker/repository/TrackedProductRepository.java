package pl.dgorecki.pricetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.dgorecki.pricetracker.entity.TrackedProduct;

@Repository
public interface TrackedProductRepository extends JpaRepository<TrackedProduct, Long>, JpaSpecificationExecutor<TrackedProduct> {
}
