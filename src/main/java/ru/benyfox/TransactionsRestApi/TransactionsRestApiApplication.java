package ru.benyfox.TransactionsRestApi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionsRestApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() { return new ModelMapper(); }


}
