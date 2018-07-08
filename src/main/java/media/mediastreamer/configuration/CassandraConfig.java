package media.mediastreamer.configuration;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.List;

/**
 * Configuration for cassandra database.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Configuration
@EnableReactiveCassandraRepositories
@Profile({ "production", "dev" })
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    private CassandraProperties cassandraProperties;

    public CassandraConfig(CassandraProperties cassandraProperties) {
        this.cassandraProperties = cassandraProperties;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(cassandraProperties.getKeyspaceName())
                .ifNotExists();

        return List.of(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return List.of(DropKeyspaceSpecification.dropKeyspace(cassandraProperties.getKeyspaceName()));
    }

    @Override
    protected String getKeyspaceName() {
        return cassandraProperties.getKeyspaceName();
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"media.mediastreamer.domain"};
    }
}
