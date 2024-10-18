package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

class ColumnDefinitionFactory {
    private final Class<?> clazz;
    private final DatabaseDialect dialect;

    ColumnDefinitionFactory(final Class<?> clazz, final DatabaseDialect dialect) {
        this.clazz = clazz;
        this.dialect = dialect;
    }

    List<ColumnDefinition> create(final Class<?> entityClass) {
        final Field[] fields = entityClass.getDeclaredFields();
        return Stream.of(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::mapToColumnDefinition)
                .toList();
    }

    private ColumnDefinition mapToColumnDefinition(final Field field) {
        return new ColumnDefinition(
                columnName(field),
                columnType(field),
                dialect.identityClause(isIdentity(field)),
                dialect.nullableClause(isNullable(field)),
                dialect.primaryKeyClause(isIdField(field))
        );
    }

    private String columnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty()) ? column.name() : field.getName().toLowerCase();
    }

    private String columnType(final Field field) {
        return ColumnType.fromJavaType(field.getType()).getDefinition(dialect);
    }

    private boolean isIdentity(final Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
    }

    private boolean isNullable(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column == null || column.nullable();
    }

    private boolean isIdField(final Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
