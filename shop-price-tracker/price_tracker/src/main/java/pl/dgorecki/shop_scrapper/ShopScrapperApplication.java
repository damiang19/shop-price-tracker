package pl.dgorecki.shop_scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ShopScrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopScrapperApplication.class, args);
	}

}
