package ru.benyfox.TransactionsRestApi.configuration;

import com.datastax.oss.driver.internal.core.auth.PlainTextAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "ru.benyfox.TransactionsRestApi.repositories.cassandra")
public class CassandraConfiguration extends AbstractCassandraConfiguration {
    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;
    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;
    @Value("${spring.cassandra.port}")
    private int port;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
	public String getContactPoints() {
		return contactPoints;
	}

    @Override
    protected int getPort() {
        return port;
    }

    @Override public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] { "ru.benyfox.TransactionsRestApi.models.ExchangeRate" };
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(keyspaceName).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        return Arrays.asList(specification);
    }
}
