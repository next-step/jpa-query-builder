package persistence.sql.ddl.view.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.view.QueryResolver;
import persistence.sql.domain.DatabaseColumn;
import persistence.sql.domain.DatabasePrimaryColumn;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLPrimaryKeyResolverTest {

    private final QueryResolver queryResolver = new MySQLPrimaryKeyResolver();

    @Test
    void should_convert_column_domain_into_query_string() throws NoSuchFieldException {
        Class<TestClass> clazz = TestClass.class;
        String query = queryResolver.toQuery(List.of(
                DatabasePrimaryColumn.fromField(clazz.getDeclaredField("id"), null),
                DatabaseColumn.fromField(clazz.getDeclaredField("test"), null)
        ));

        assertThat(query).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT,test VARCHAR(300)");
    }

    private class TestClass {

        @Id
        @GeneratedValue
        private Long id;

        @Column(length = 300)
        private String test;
    }

}
