package pl.dgorecki.scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dgorecki.scrapper.entity.Shop;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByShopUrl(String url);

    List<Shop> findAllByNameIn(List<String> name);
}
