package persistence.sql.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static persistence.sql.CommonConstant.COLUMN_SEPARATOR;

public class DatabaseTable {

    private static final String AND = " and ";

    private final String name;

    private final List<DatabaseColumn> columns;

    public DatabaseTable(Class<?> clazz) {
        this.name = getTableName(clazz);
        this.columns = buildColumns(clazz, null);
    }

    public <T> DatabaseTable(T entity) {
        Class<?> clazz = entity.getClass();
        this.name = getTableName(clazz);
        this.columns = buildColumns(clazz, entity);
    }

    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("entity annotation is required");
        }
        Table table = clazz.getAnnotation(Table.class);
        if (table != null && table.name().length() > 0) {
            return table.name();
        }
        return clazz.getSimpleName();
    }

    private List<DatabaseColumn> buildColumns(Class<?> clazz, Object object) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isMappingColumn)
                .map(column -> buildColumn(column, object))
                .collect(Collectors.toList());
    }

    private boolean isMappingColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private DatabaseColumn buildColumn(Field field, Object object) {
        ColumnName name = new ColumnName(field);
        ColumnLength length = new ColumnLength(field);
        ColumnValue value = new ColumnValue(field, object);

        if (field.isAnnotationPresent(Id.class)) {
            return new DatabasePrimaryColumn(name, value, length, field);
        }

        ColumnNullable nullable = ColumnNullable.getInstance(field);
        return new DatabaseColumn(name, value, length, nullable);
    }

    public String getName() {
        return name;
    }

    public List<DatabaseColumn> getColumns() {
        return columns;
    }

    public String columnClause() {
        return insertClause(DatabaseColumn::getName);
    }

    public String valueClause() {
        return insertClause(DatabaseColumn::getValue);
    }

    private String insertClause(Function<DatabaseColumn, String> convert) {
        return columns.stream()
                .filter(this::notAutoIncrementColumn)
                .map(convert)
                .reduce((columnA, columnB) -> String.join(COLUMN_SEPARATOR, columnA, columnB))
                .orElseThrow(IllegalStateException::new);
    }

    private boolean notAutoIncrementColumn(DatabaseColumn column) {
        if (column instanceof DatabasePrimaryColumn) {
            DatabasePrimaryColumn primaryColumn = (DatabasePrimaryColumn) column;
            return primaryColumn.getValue() != null;
        }
        return true;
    }

    public String getIdColumnName() {
        return columns.stream()
                .filter(column -> column instanceof DatabasePrimaryColumn)
                .map(DatabaseColumn::getName)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public String whereClause() {
        return columns.stream()
                .map(DatabaseColumn::whereClause)
                .reduce((columnA, columnB) -> String.join(AND, columnA, columnB))
                .orElseThrow(IllegalStateException::new);
    }

}
