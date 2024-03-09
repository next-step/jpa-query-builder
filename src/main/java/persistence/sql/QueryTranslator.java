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

    public String getCreateTableQuery(final Class<?> clazz) {
        return String.format(
            "CREATE TABLE %s (%s)",
            getTableNameFrom(clazz),
            getTableColumnDefinitionFrom(clazz)
        );
    }

    public String getDropTableQuery(Class<?> clazz) {
        return String.format(
            "DROP TABLE %s",
            getTableNameFrom(clazz)
        );
    }

    public String getInsertQuery(Object entity) {
        Class<?> entityClass = entity.getClass();

        return String.format(
            "INSERT INTO %s (%s) VALUES (%s)",
            getTableNameFrom(entityClass),
            getColumnNamesClause(entityClass),
            getColumnValueClause(entity)
        );
    }

    public String getTableNameFrom(Class<?> clazz) {
        return Stream.of(
                getSchemaNameFrom(clazz),
                getOnlyTableNameFrom(clazz)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(SCHEMA_TABLE_DELIMITER));
    }

    public String getTableColumnDefinitionFrom(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
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

    public String getColumnNamesClause(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
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

    protected String getOnlyTableNameFrom(final Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            return getOnlyTableNameFrom(table);
        }

        return clazz.getSimpleName();
    }

    protected String getOnlyTableNameFrom(Table table) {
        if (!table.name().isEmpty()) {
            return table.name();
        }

        return StringConstants.EMPTY_STRING;
    }

    protected String getSchemaNameFrom(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
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
