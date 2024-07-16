package ru.benyfox.TransactionsRestApi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.benyfox.TransactionsRestApi.repositories.jpa")
public class JpaConfiguration {
}
