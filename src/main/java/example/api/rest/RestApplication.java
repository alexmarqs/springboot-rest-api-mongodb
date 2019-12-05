package example.api.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Rest API spring boot application.
 */
@SpringBootApplication
public class RestApplication {

	private static final Logger LOGGER = LogManager.getLogger(RestApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Rest Api Application...");
		SpringApplication.run(RestApplication.class, args);
	}

}
