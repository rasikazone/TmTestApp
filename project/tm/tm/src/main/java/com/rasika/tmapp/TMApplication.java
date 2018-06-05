package com.rasika.tmapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rasika.tmapp.listener.FileListener;

@SpringBootApplication
@EnableJpaAuditing

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.rasika.tmapp.repo"})

public class TMApplication {


	public static void main(String[] args) {
		
		
		SpringApplication.run(TMApplication.class, args);
		
		new FileListener().start();
		
	}
}
