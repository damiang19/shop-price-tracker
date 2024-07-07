package pl.dgorecki.shop_scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.dgorecki.shop_scrapper.entity.TrackedProduct;

@Repository
public interface TrackedProductRepository extends JpaRepository<TrackedProduct, Long>, JpaSpecificationExecutor<TrackedProduct> {
}
