package com.gdscsolutionchallenge.shareBite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShareBiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareBiteApplication.class, args);
	}

}

// push test1
