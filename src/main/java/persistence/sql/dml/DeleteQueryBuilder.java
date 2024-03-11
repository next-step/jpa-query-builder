package persistence.sql.dml;

import static persistence.sql.ddl.common.StringConstants.*;

import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import persistence.sql.AbstractQueryBuilder;
import persistence.sql.ddl.TableQueryBuilder;

public class DeleteQueryBuilder extends AbstractQueryBuilder {
    private final TableQueryBuilder tableQueryBuilder;

    public DeleteQueryBuilder(
        TableQueryBuilder tableQueryBuilder
    ) {
        this.tableQueryBuilder = tableQueryBuilder;
    }

    public String getDeleteAllQuery(Class<?> entityClass) {
        return String.format(
            "DELETE FROM %s",
            tableQueryBuilder.getTableNameFrom(entityClass)
        );
    }

    public String getDeleteByIdQuery(Class<?> entityClass, Object id) {
        return String.format(
            "DELETE FROM %s WHERE %s = %s",
            tableQueryBuilder.getTableNameFrom(entityClass),
            getPrimaryKeyColumnName(entityClass),
            getPrimaryKeyValueQueryFromEntityClassAndId(entityClass, id)
        );
    }

    public String getDeleteQueryFromEntity(Object entity) {
        return getDeleteQueryFromEntity(entity.getClass(), entity);
    }

    private String getDeleteQueryFromEntity(Class<?> entityClass, Object entity) {
        return String.format(
            "DELETE FROM %s WHERE %s = %s",
            tableQueryBuilder.getTableNameFrom(entityClass),
            getPrimaryKeyColumnName(entityClass),
            getPrimaryKeyValueQueryFromEntityClassAndEntityObject(entityClass, entity)
        );
    }

    private String getPrimaryKeyColumnName(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .map(this::getColumnNameFrom)
            .orElseThrow(() -> new IllegalStateException(PRIMARY_KEY_NOT_FOUND));
    }

    private String getPrimaryKeyValueQueryFromEntityClassAndId(Class<?> entityClass, Object id) {
        Field primaryKeyField = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(PRIMARY_KEY_NOT_FOUND));

        if (!primaryKeyField.getType().equals(id.getClass())) {
            throw new IllegalStateException("Primary key type mismatch");
        }

        return getColumnValueFromObject(id);
    }

    private String getPrimaryKeyValueQueryFromEntityClassAndEntityObject(Class<?> entityClass, Object entity) {
        try {
            Field primaryKeyField = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(PRIMARY_KEY_NOT_FOUND));

            primaryKeyField.setAccessible(true);

            Object id = primaryKeyField.get(entity);

            return getColumnValueFromObject(id);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
