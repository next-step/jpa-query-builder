package persistence.sql.dml;

import static persistence.sql.ddl.common.StringConstants.COLUMN_DEFINITION_DELIMITER;
import static persistence.sql.ddl.common.StringConstants.PRIMARY_KEY_NOT_FOUND;

import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import persistence.sql.AbstractQueryTranslator;
import persistence.sql.ddl.TableQueryBuilder;

public class SelectQueryTranslator extends AbstractQueryTranslator {
    private final TableQueryBuilder tableQueryBuilder;

    public SelectQueryTranslator(
        TableQueryBuilder tableQueryBuilder
    ) {
        this.tableQueryBuilder = tableQueryBuilder;
    }

    public String getSelectAllQuery(Class<?> entityClass) {
        return String.format(
            "SELECT %s FROM %s",
            getColumnNamesClause(entityClass),
            tableQueryBuilder.getTableNameFrom(entityClass)
        );
    }

    public String getSelectByIdQuery(Class<?> entityClass, Object id) {
        return String.format(
            "SELECT %s FROM %s WHERE %s = %s",
            getColumnNamesClause(entityClass),
            tableQueryBuilder.getTableNameFrom(entityClass),
            getPrimaryKeyColumnName(entityClass),
            getPrimaryKeyValueQueryFromEntityClassAndId(entityClass, id)
        );
    }

    public String getSelectCountQuery(Class<?> entityClass) {
        return String.format(
            "SELECT COUNT(%s) FROM %s",
            getPrimaryKeyColumnName(entityClass),
            tableQueryBuilder.getTableNameFrom(entityClass)
        );
    }

    private String getColumnNamesClause(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
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
}
