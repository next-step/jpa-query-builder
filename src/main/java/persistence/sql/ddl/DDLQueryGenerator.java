package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.ddl.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DDLQueryGenerator {
    private final Dialect dialect;

    DDLQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateCreateQuery(final Class<?> entityClazz) {
        checkIsEntity(entityClazz);

        final String tableNameClause = getTableName(entityClazz);

        List<Field> fields = Arrays.stream(entityClazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());

        final String columnClause = fields.stream()
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(", "));

        Field primaryKeyField = fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(IdAnnotationMissingException::new);
        final String primaryKeyClause = "PRIMARY KEY (" + primaryKeyField.getName() + ")";

        return String.format("CREATE TABLE %s (%s, %s)", tableNameClause, columnClause, primaryKeyClause);
    }

    public String generateDropTableQuery(final Class<?> entityClazz) {
        checkIsEntity(entityClazz);

        final String tableName = getTableName(entityClazz);
        return String.format("DROP TABLE %s", tableName);
    }

    private void checkIsEntity(Class<?> entityClazz) {
        if (!entityClazz.isAnnotationPresent(Entity.class)) {
            throw new AnnotationMissingException("Entity 어노테이션이 없습니다.");
        }
    }

    private String getColumnDefinition(Field field) {
        StringBuilder sb = new StringBuilder();
        Column column = field.getAnnotation(Column.class);

        sb.append(getColumnName(field, column));
        sb.append(" ");
        sb.append(getColumnType(field, column));

        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue != null) {
            sb.append(" ");
            sb.append(dialect.mapGenerationType(generatedValue.strategy()));
        }

        return sb.toString();
    }

    private String getColumnType(Field field, Column column) {
        String columnType = dialect.mapDataType(field.getType());
        if(column != null && !column.nullable()) {
            columnType += " NOT NULL";
        }
        return columnType;
    }

    private static String getColumnName(Field field, Column column) {
        String columnName = field.getName();
        if(column != null && !column.name().isEmpty()) {
            columnName = column.name();
        }
        return columnName;
    }

    private String getTableName(Class<?> entityClazz) {
        Table table = entityClazz.getAnnotation(Table.class);
        if (table == null) {
            return entityClazz.getSimpleName().toLowerCase();
        }
        return table.name();
    }
}
