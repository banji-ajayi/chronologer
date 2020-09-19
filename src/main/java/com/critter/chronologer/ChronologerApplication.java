package com.critter.chronologer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChronologerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChronologerApplication.class, args);
		System.out.println("hello");
	}

}
