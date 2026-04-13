package com.bovexo.feed_cost_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class FeedCostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedCostServiceApplication.class, args);
	}

}
