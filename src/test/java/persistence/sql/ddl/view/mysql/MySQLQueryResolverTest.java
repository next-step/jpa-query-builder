package persistence.sql.ddl.view.mysql;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.view.QueryResolver;
import persistence.sql.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLQueryResolverTest {

    private final QueryResolver queryResolver = new MySQLPrimaryKeyResolver();

    @Test
    void should_convert_column_domain_into_query_string() {

        List<DatabaseColumn> columns = List.of(
                new DatabasePrimaryColumn(new ColumnName("id"), new ColumnValue(Long.class,null), null, GenerationType.IDENTITY),
                new DatabaseColumn(new ColumnName("test"), new ColumnValue(String.class, null), new ColumnLength(300), ColumnNullable.NULLABLE)
        );

        String query = queryResolver.toQuery(columns);

        assertThat(query).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT, test VARCHAR(300)");
    }

}
