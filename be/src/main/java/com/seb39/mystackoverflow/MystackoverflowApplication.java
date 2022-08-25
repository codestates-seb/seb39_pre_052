package com.seb39.mystackoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class MystackoverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MystackoverflowApplication.class, args);
	}

}
