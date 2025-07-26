package pl.dgorecki.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dgorecki.scrapper.service.dto.NotificationDTO;

@Service
@RequiredArgsConstructor
public class EventDrivenServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(EventDrivenServiceImpl.class);

    private final StreamBridge streamBridge;

    @Transactional
    public void sendCommunication() {
        NotificationDTO notificationDTO = new NotificationDTO("Siema","Elo","dd@dd.dd","123123123",1L);
        log.info("Sending communication : {}", notificationDTO);
        var result  = streamBridge.send("sendCommunication-out-0", notificationDTO);
        log.info("Is communication request successfully processed ? : {}", result);
    }
}
