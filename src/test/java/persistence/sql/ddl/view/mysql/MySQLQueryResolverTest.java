package persistence.sql.ddl.view.mysql;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.domain.*;
import persistence.sql.ddl.view.QueryResolver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLQueryResolverTest {

    @Test
    void should_convert_column_domain_into_query_string() {
        QueryResolver queryResolver = new MySQLPrimaryKeyResolver();
        List<DatabaseColumn> columns = List.of(
                new DatabasePrimaryColumn(new ColumnName("id"), Long.class, null, GenerationType.IDENTITY),
                new DatabaseColumn(new ColumnName("test"), String.class, new ColumnLength(300), ColumnNullable.NULLABLE)
        );

        String query = queryResolver.toQuery(columns);

        assertThat(query).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT, test VARCHAR(300)");
    }

}
