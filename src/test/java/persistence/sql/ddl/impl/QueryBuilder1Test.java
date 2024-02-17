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
import persistence.sql.ddl.Person1;

class QueryBuilder1Test {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryBuilder2Test.class);

    private final QueryBuilder1 queryBuilder = new QueryBuilder1();

    @Test
    void createDDL() {
        String ddl = queryBuilder.buildDDL(Person1.class);

        log.debug("DDL: {}", ddl);

        assertThat(ddl)
            .isEqualTo("CREATE TABLE Person1 (id BIGINT AUTO_INCREMENT, name VARCHAR(255), age INTEGER)");
    }

    @Test
    void getTableNameByClassName() {
        String tableName = queryBuilder.getTableNameFrom(Person1.class);

        log.debug("Table name: {}", tableName);

        assertThat(tableName).isEqualTo("Person1");
    }


    @Test
    void getColumnDefinitionStatement() {
        String columnDefinitionStatement = queryBuilder.getTableColumnDefinitionFrom(Person1.class);

        log.debug("Column definition statement: {}", columnDefinitionStatement);

        assertThat(columnDefinitionStatement).isEqualTo("id BIGINT AUTO_INCREMENT, name VARCHAR(255), age INTEGER");
    }

    @Test
    void getColumnDefinitionStatementFromField() {
        List<Field> columnFields = Arrays.stream(Person1.class.getDeclaredFields())
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
                    assertThat(columnDefinitionStatement).isEqualTo("name VARCHAR(255)");
                    break;
                case "age":
                    assertThat(columnDefinitionStatement).isEqualTo("age INTEGER");
                    break;
                default:
                    fail("Unexpected field name: " + fieldName);
            }
        });
    }
}
