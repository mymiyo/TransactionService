package ru.benyfox.TransactionsRestApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "ru.benyfox.TransactionsRestApi.repositories.cassandra")
@EnableJpaRepositories(basePackages = "ru.benyfox.TransactionsRestApi.repositories.jpa")
public class ApplicationConfig extends AbstractCassandraConfiguration {
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Override
    protected String getKeyspaceName() {
        return this.keyspaceName;
    }
}
