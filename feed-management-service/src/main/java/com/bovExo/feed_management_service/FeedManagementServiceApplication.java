package com.bovExo.feed_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FeedManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedManagementServiceApplication.class, args);
	}

}
