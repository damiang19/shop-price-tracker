package pl.dgorecki.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ScrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrapperApplication.class, args);
	}

}
