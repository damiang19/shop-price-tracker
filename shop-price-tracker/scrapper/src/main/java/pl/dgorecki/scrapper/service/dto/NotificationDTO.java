package pl.dgorecki.scrapper.service.dto;

public record NotificationDTO(String title, String content, String email, String phoneNumber, Long productId) {
}

