package persistence.sql.dml;

import static persistence.sql.ddl.common.StringConstants.COLUMN_DEFINITION_DELIMITER;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import persistence.sql.AbstractQueryBuilder;
import persistence.sql.ddl.TableQueryBuilder;

public class InsertQueryBuilder extends AbstractQueryBuilder {
    private final TableQueryBuilder tableQueryBuilder;

    public InsertQueryBuilder(TableQueryBuilder tableQueryBuilder) {
        this.tableQueryBuilder = tableQueryBuilder;
    }

    public String getInsertQuery(Object entity) {
        Class<?> entityClass = entity.getClass();

        return String.format(
            "INSERT INTO %s (%s) VALUES (%s)",
            tableQueryBuilder.getTableNameFrom(entityClass),
            getColumnNamesWithoutPrimaryKey(entityClass),
            getColumnValuesQuery(entity)
        );
    }

    private String getColumnValuesQuery(Object entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
            .map(field -> getColumnValueFromEntity(entity, field))
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    private String getColumnNamesWithoutPrimaryKey(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    private String getColumnValueFromEntity(Object entity, Field field) {
        try {
            field.setAccessible(true);
            Object columnValue = field.get(entity);

            return getColumnValueFromObject(columnValue);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
