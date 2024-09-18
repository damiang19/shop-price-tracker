package pl.dgorecki.pricetracker.service;


import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dgorecki.pricetracker.entity.TrackedProduct;
import pl.dgorecki.pricetracker.entity.TrackedProduct_;
import pl.dgorecki.pricetracker.repository.TrackedProductRepository;
import pl.dgorecki.pricetracker.service.criteria.TrackedProductCriteria;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;
import pl.dgorecki.pricetracker.service.mapper.TrackedProductMapper;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackedProductQueryService {

    private final TrackedProductRepository trackedProductRepository;
    private final TrackedProductMapper trackedProductMapper;

    @Transactional
    public List<TrackedProductDTO> findByCriteria(TrackedProductCriteria trackedProductCriteria, Pageable pageable) {
        Specification<TrackedProduct> specification = createSpecification(trackedProductCriteria);
        return trackedProductMapper.toDto(trackedProductRepository.findAll(specification, pageable).getContent());
    }

    private Specification<TrackedProduct> createSpecification(TrackedProductCriteria trackedProductCriteria) {
        Specification<TrackedProduct> specification = Specification.where(null);
        if (trackedProductCriteria.getProductNameStartsWith() != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Predicate userName = criteriaBuilder.like(criteriaBuilder.upper(root.get(TrackedProduct_.productName)),
                        (trackedProductCriteria.getProductNameStartsWith() + "%").toUpperCase());
                return criteriaBuilder.and(userName);
            });
        }
        if (trackedProductCriteria.getCreatedLessThan() != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Predicate userName = criteriaBuilder.lessThan(root.get(TrackedProduct_.created),
                        (Instant.parse(trackedProductCriteria.getCreatedLessThan())));
                return criteriaBuilder.and(userName);
            });
        }
        return specification;
    }
}
