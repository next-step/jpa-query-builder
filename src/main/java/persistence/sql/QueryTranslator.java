package persistence.sql;

import static persistence.sql.ddl.common.StringConstants.COLUMN_DEFINITION_DELIMITER;
import static persistence.sql.ddl.common.StringConstants.SCHEMA_TABLE_DELIMITER;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.common.StringConstants;
import persistence.sql.ddl.constraints.strategy.ConstraintsStrategy;
import persistence.sql.ddl.constraints.strategy.DefaultConstraintsStrategy;
import persistence.sql.ddl.type.DataTypeMapping;
import persistence.sql.ddl.type.impl.DefaultDataTypeMapping;
import persistence.sql.dml.ColumnValueTranslator;

public class QueryTranslator {

    protected final ConstraintsStrategy constraintsStrategy;

    protected final DataTypeMapping dataTypeMapping;

    protected final ColumnValueTranslator columnValueTranslator;

    public QueryTranslator() {
        this(
            new DefaultConstraintsStrategy(),
            new DefaultDataTypeMapping(),
            new ColumnValueTranslator()
        );
    }

    protected QueryTranslator(
        ConstraintsStrategy constraintsStrategy,
        DataTypeMapping dataTypeMapping,
        ColumnValueTranslator columnValueTranslator
    ) {
        this.constraintsStrategy = constraintsStrategy;
        this.dataTypeMapping = dataTypeMapping;
        this.columnValueTranslator = columnValueTranslator;
    }

    public String getCreateTableQuery(final Class<?> entityClass) {
        return String.format(
            "CREATE TABLE %s (%s)",
            getTableNameFrom(entityClass),
            getTableColumnDefinitionFrom(entityClass)
        );
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return String.format(
            "DROP TABLE %s",
            getTableNameFrom(entityClass)
        );
    }

    public String getInsertQuery(Object entity) {
        Class<?> entityClass = entity.getClass();

        return String.format(
            "INSERT INTO %s (%s) VALUES (%s)",
            getTableNameFrom(entityClass),
            getColumnNamesClauseWithoutPrimaryKey(entityClass),
            getColumnValueClause(entity)
        );
    }

    public String getSelectAllQuery(Class<?> entityClass) {
        return String.format(
            "SELECT %s FROM %s",
            getColumnNamesClause(entityClass),
            getTableNameFrom(entityClass)
        );
    }

    public String getSelectByIdQuery(Class<?> entityClass, Object id) {
        return String.format(
            "SELECT %s FROM %s WHERE %s = %s",
            getColumnNamesClause(entityClass),
            getTableNameFrom(entityClass),
            getPrimaryKeyColumnName(entityClass),
            columnValueTranslator.getPrimaryKeyValueClauseFromEntityClassAndId(entityClass, id)
        );
    }

    public String getDeleteAllQuery(Class<?> entityClass) {
        return String.format(
            "DELETE FROM %s",
            getTableNameFrom(entityClass)
        );
    }

    public String getDeleteByIdQuery(Class<?> entityClass, Object id) {
        return String.format(
            "DELETE FROM %s WHERE %s = %s",
            getTableNameFrom(entityClass),
            getPrimaryKeyColumnName(entityClass),
            columnValueTranslator.getPrimaryKeyValueClauseFromEntityClassAndId(entityClass, id)
        );
    }

    public String getDeleteQueryFromEntity(Class<?> entityClass, Object entity) {
        return String.format(
            "DELETE FROM %s WHERE %s = %s",
            getTableNameFrom(entityClass),
            getPrimaryKeyColumnName(entityClass),
            columnValueTranslator.getPrimaryKeyValueClauseFromEntityClassAndEntityObject(entityClass, entity)
        );
    }


    private String getPrimaryKeyColumnName(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .map(this::getColumnNameFrom)
            .orElseThrow(() -> new IllegalStateException("Primary key not found"));
    }

    public String getTableNameFrom(Class<?> entityClass) {
        return Stream.of(
                getSchemaNameFrom(entityClass),
                getOnlyTableNameFrom(entityClass)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(SCHEMA_TABLE_DELIMITER));
    }

    public String getTableColumnDefinitionFrom(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .map(this::getColumnDefinitionFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    public String getColumnDefinitionFrom(Field field) {
        return Stream.of(
                getColumnNameFrom(field),
                getColumnDataTypeDefinitionFrom(field),
                getColumnConstraintsFrom(field)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(" "));
    }

    public String getColumnNameFrom(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        Column column = field.getAnnotation(Column.class);

        if (column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    public String getColumnDataTypeDefinitionFrom(Field field) {
        return dataTypeMapping.getDataTypeDefinitionFrom(field);
    }

    public String getColumnConstraintsFrom(Field field) {
        return constraintsStrategy.getConstraintsFrom(field);
    }

    public String getColumnValueClause(Object entity) {
        return columnValueTranslator.getColumnValueClause(entity);
    }

    protected String getColumnNamesClause(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    protected String getColumnNamesClauseWithoutPrimaryKey(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    protected static Stream<Field> getColumnFieldStream(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1));
    }

    protected String getOnlyTableNameFrom(final Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return getOnlyTableNameFrom(table);
        }

        return entityClass.getSimpleName();
    }

    protected String getOnlyTableNameFrom(Table table) {
        if (!table.name().isEmpty()) {
            return table.name();
        }

        return StringConstants.EMPTY_STRING;
    }

    protected String getSchemaNameFrom(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return  getSchemaNameFrom(table);
        }

        return StringConstants.EMPTY_STRING;
    }

    protected String getSchemaNameFrom(Table table) {
        if (!table.schema().isEmpty()) {
            return table.schema();
        }

        return StringConstants.EMPTY_STRING;
    }
}
