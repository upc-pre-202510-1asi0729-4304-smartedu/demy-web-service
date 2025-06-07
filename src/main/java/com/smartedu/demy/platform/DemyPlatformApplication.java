package com.smartedu.demy.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * DemyPlatformApplication
 *
 * @summary
 * The main class of the Demy Web Service application.
 * It is responsible for starting the Spring Boot application.
 * It also enables JPA auditing.
 *
 * @since 1.0
 */
@EnableJpaAuditing
@SpringBootApplication
public class DemyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemyPlatformApplication.class, args);
	}

}
