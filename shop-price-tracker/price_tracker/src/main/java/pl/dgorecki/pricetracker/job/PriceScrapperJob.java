package pl.dgorecki.pricetracker.job;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.dgorecki.pricetracker.service.TrackedProductArchiveService;
import pl.dgorecki.pricetracker.service.TrackedProductQueryService;
import pl.dgorecki.pricetracker.service.TrackedProductService;
import pl.dgorecki.pricetracker.service.criteria.TrackedProductCriteria;
import pl.dgorecki.pricetracker.service.dto.TrackedProductDTO;

import java.time.Instant;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class PriceScrapperJob {

    private final TrackedProductService trackedProductService;
    private final TrackedProductArchiveService trackedProductArchiveService;
    private final TrackedProductQueryService trackedProductQueryService;
    private final Logger log = LoggerFactory.getLogger(getClass());
    // dodac logowanie debug
    // dodac algorytm do przeszukiwania elementow html w celu wyscrapowania aktualnej ceny (narazie dziala tylko dla morele)

    @Scheduled(fixedDelay = 3_600_000)
    public void scheduleFixedDelayTask() {
        log.info("Job started");
        Pageable secondPageWithFiveElements = PageRequest.of(0, 100);
        TrackedProductCriteria trackedProductCriteria = new TrackedProductCriteria();
        trackedProductCriteria.setCreatedLessThan(Instant.now().toString());
        //TODO : pobierz tylko wtedy gdy aktualna data zescrapownia produktu jest starsza niz dzien dzisiejszy
        List<TrackedProductDTO> trackedProductDTOList = trackedProductQueryService.findByCriteria(trackedProductCriteria, secondPageWithFiveElements);
        trackedProductArchiveService.saveAll(trackedProductDTOList);
        trackedProductService.updateProductsByActualPrices(trackedProductDTOList);
        log.info("Job end");
    }
}
