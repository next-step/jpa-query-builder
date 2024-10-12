package persistence.sql.ddl;

import jakarta.persistence.Entity;

import java.util.stream.Collectors;

public class QueryBuilder {

    private static final String COLUMN_JOIN_DELIMITER = ", ";

    public String buildCreateDdl(Class<?> clazz) {
        Columns columns = Columns.from(clazz);
        return "create table " +
                generateTableName(clazz) +
                " (" +
                getDefinitions(columns) +
                ");";
    }

    private String getDefinitions(Columns columns) {
        return generateColumnDefinitions(columns) + ", primary key (" + columns.getPrimaryKeyColumn().getName() + ")";
    }

    private String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity가 아닙니다");
        }
        String name = clazz.getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private String generateColumnDefinitions(Columns columns) {
        return columns.getColumns().stream()
                .map(this::generateColumnDefinition)
                .collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }

    private String generateColumnDefinition(Column column) {
        if (column.hasPrimaryKey()) {
            return column.getName() + " " + column.getSqlType() + " not null";
        }
        return column.getName() + " " + column.getSqlType();
    }
}
