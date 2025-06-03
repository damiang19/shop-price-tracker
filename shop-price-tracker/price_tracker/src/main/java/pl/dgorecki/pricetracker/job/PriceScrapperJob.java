package pl.dgorecki.pricetracker.job;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.dgorecki.pricetracker.service.TrackedProductArchiveService;
import pl.dgorecki.pricetracker.service.TrackedProductQueryService;
import pl.dgorecki.pricetracker.service.TrackedProductService;
import pl.dgorecki.pricetracker.service.criteria.TrackedProductCriteria;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class PriceScrapperJob {

    private final TrackedProductService trackedProductService;
    private final TrackedProductArchiveService trackedProductArchiveService;
    private final TrackedProductQueryService trackedProductQueryService;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Scheduled(fixedDelay = 60000)
    public void scrapActualPricesOfTrackedProducts() {
        log.info("Job started");
        Pageable secondPageWithFiveElements = PageRequest.of(0, 20, Sort.by("created").descending());
        TrackedProductCriteria trackedProductCriteria = new TrackedProductCriteria();
        trackedProductCriteria.setCreatedLessThan(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toString());
        List<TrackedProductDTO> trackedProductDTOList = trackedProductQueryService.findByCriteria(trackedProductCriteria, secondPageWithFiveElements);
        trackedProductArchiveService.saveAll(trackedProductDTOList);
        trackedProductService.updateProductsByActualPrices(trackedProductDTOList);
        log.info("Job end");
    }
}
