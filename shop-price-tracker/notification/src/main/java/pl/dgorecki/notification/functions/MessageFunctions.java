package pl.dgorecki.notification.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dgorecki.notification.dto.NotificationDto;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<NotificationDto, NotificationDto> email() {
        return notificationDto -> {
          log.info("Sending email : " + notificationDto.toString());
          return notificationDto;
        };
    }

    @Bean
    public Function<NotificationDto, Long> sms() {
        return notificationDto -> {
            log.info("Sending sms : " + notificationDto.toString());
            return notificationDto.productId();
        };
    }
}
