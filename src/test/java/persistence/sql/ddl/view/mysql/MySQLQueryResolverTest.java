package persistence.sql.ddl.view.mysql;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.domain.DatabaseColumn;
import persistence.sql.ddl.domain.DatabasePrimaryColumn;
import persistence.sql.ddl.view.QueryResolver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLQueryResolverTest {

    @Test
    void should_convert_column_domain_into_query_string() {
        QueryResolver queryResolver = new MySQLQueryResolver();
        List<DatabaseColumn> columns = List.of(
                new DatabasePrimaryColumn("id", Long.class, null, GenerationType.IDENTITY),
                new DatabaseColumn("test", String.class, 300, true)
        );

        String query = queryResolver.toQuery(columns);

        assertThat(query).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT, test VARCHAR(300)");
    }

}
