package persistence.sql.ddl;

import static persistence.sql.ddl.common.StringConstants.SCHEMA_TABLE_DELIMITER;

import jakarta.persistence.Table;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.common.StringConstants;

public class TableQueryBuilder {
    public String getTableNameFrom(Class<?> entityClass) {
        return Stream.of(
                getSchemaNameFrom(entityClass),
                getOnlyTableNameFrom(entityClass)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(SCHEMA_TABLE_DELIMITER));
    }

    public String getSchemaNameFrom(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return getSchemaNameFrom(table);
        }

        return StringConstants.EMPTY_STRING;
    }

    public String getSchemaNameFrom(Table table) {
        if (!table.schema().isEmpty()) {
            return table.schema();
        }

        return StringConstants.EMPTY_STRING;
    }

    public String getOnlyTableNameFrom(final Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return getOnlyTableNameFrom(table);
        }

        return entityClass.getSimpleName();
    }

    public String getOnlyTableNameFrom(Table table) {
        if (!table.name().isEmpty()) {
            return table.name();
        }

        return StringConstants.EMPTY_STRING;
    }
}
