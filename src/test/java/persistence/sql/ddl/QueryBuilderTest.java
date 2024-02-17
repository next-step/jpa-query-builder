package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class QueryBuilderTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryBuilderTest.class);

    private final QueryBuilder queryBuilder = new QueryBuilder();

    @Entity
    @Table(name = "PERSON_2")
    private static class Person2 {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;
    }

    @Test
    void createDDL() {
        String ddl = queryBuilder.buildDDL(Person.class);

        assertThat(ddl)
            .isEqualTo("CREATE TABLE Person (id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
    }

    @Test
    void getTableNameByClassName() {
        String tableName = queryBuilder.getTableName(Person.class);

        assertThat(tableName).isEqualTo("Person");
    }

    @Test
    void getTableNameByTableAnnotation() {
        String tableName = queryBuilder.getTableName(Person2.class);

        assertThat(tableName).isEqualTo("PERSON_2");
    }

    @Test
    void getColumnDefinitionStatement() {
        String columnDefinitionStatement = queryBuilder.getTableColumnDefinition(Person.class);

        assertThat(columnDefinitionStatement).isEqualTo("id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL");
    }

    @Test
    void getColumnDefinitionStatementFromField() {
        List<Field> columnFields = Arrays.stream(Person.class.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class))
            .collect(Collectors.toList());

        columnFields.forEach(field -> {
            log.debug("Field: {}", field.getName());

            String columnDefinitionStatement = queryBuilder.getColumnDefinitionStatementFromField(field);

            log.debug("Column definition statement: {}", columnDefinitionStatement);
        });
    }

    @Test
    void getColumnConstraints() {
        List<Field> columnFields = Arrays.stream(Person.class.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class))
            .collect(Collectors.toList());

        columnFields.forEach(field -> {
            log.debug("Field: {}", field.getName());

            String columnConstraints = queryBuilder.getColumnConstraints(field);

            log.debug("Column constraints: {}", columnConstraints);
        });
    }

}
