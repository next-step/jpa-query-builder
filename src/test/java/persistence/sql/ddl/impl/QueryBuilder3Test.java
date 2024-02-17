package persistence.sql.ddl.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import persistence.sql.ddl.Person3;

class QueryBuilder3Test {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryBuilder3Test.class);

    private final Class<?> entityClass = Person3.class;

    private final QueryBuilder3 queryBuilder = new QueryBuilder3();

    @Test
    void createDDL() {
        String ddl = queryBuilder.buildDDL(entityClass);

        log.debug("DDL: {}", ddl);

        assertThat(ddl)
            .isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) UNIQUE NOT NULL)");
    }

    @Test
    void getTableNameByClassName() {
        String tableName = queryBuilder.getTableNameFrom(entityClass);

        log.debug("Table name: {}", tableName);

        assertThat(tableName).isEqualTo("users");
    }

    @Test
    void getColumnDefinitionStatement() {
        String columnDefinitionStatement = queryBuilder.getTableColumnDefinitionFrom(entityClass);

        log.debug("Column definition statement: {}", columnDefinitionStatement);

        assertThat(columnDefinitionStatement).isEqualTo("id BIGINT AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) UNIQUE NOT NULL");
    }

    @Test
    void getColumnDefinitionStatementFromField() {
        List<Field> columnFields = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Id.class))
            .collect(Collectors.toList());

        Map<String, String> fieldNameToColumnDefinitionStatement = columnFields.stream()
            .collect(
                Collectors.toMap(
                    Field::getName,
                    queryBuilder::getColumnDefinitionStatementFrom
                )
            );

        fieldNameToColumnDefinitionStatement.forEach((fieldName, columnDefinitionStatement) -> {
            log.debug("Field: {}", fieldName);
            log.debug("Column definition statement: {}", columnDefinitionStatement);

            switch (fieldName) {
                case "id":
                    assertThat(columnDefinitionStatement).isEqualTo("id BIGINT AUTO_INCREMENT");
                    break;
                case "name":
                    assertThat(columnDefinitionStatement).isEqualTo("nick_name VARCHAR(255)");
                    break;
                case "age":
                    assertThat(columnDefinitionStatement).isEqualTo("old INTEGER");
                    break;
                case "email":
                    assertThat(columnDefinitionStatement).isEqualTo("email VARCHAR(255) UNIQUE NOT NULL");
                    break;
                default:
                    fail("Unexpected field name: " + fieldName);
            }
        });
    }
}
