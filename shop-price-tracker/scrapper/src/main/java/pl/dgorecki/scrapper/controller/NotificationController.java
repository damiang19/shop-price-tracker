package pl.dgorecki.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dgorecki.scrapper.service.impl.EventDrivenServiceImpl;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final EventDrivenServiceImpl eventDrivenService;


    @GetMapping("/notification/sendMessage")
    public ResponseEntity<Void> sendCommunication() {
        eventDrivenService.sendCommunication();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
