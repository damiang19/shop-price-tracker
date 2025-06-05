package pl.dgorecki.notification.dto;

public record NotificationDto(String title, String content, String email, String phoneNumber, Long productId) {
}
