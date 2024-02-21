package persistence.sql.ddl.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.mapping.PrimaryKeyGenerationType;
import persistence.sql.ddl.converter.TypeConverter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.StringJoiner;

public class Column {

    private static final String COLUMN_OPEN_BRACKET = " ( ";
    private static final String COLUMN_CLOSE_BRACKET = " );";
    private static final String SEPARATOR = ", ";
    private static final String SPACE = " ";
    private static final String PK_QUERY = " NOT NULL PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";
    private static final String NOTHING = "";

    private final TypeConverter converter;
    private final PrimaryKeyGenerationType generationType;

    public Column(TypeConverter converter, PrimaryKeyGenerationType generationType) {
        this.converter = converter;
        this.generationType = generationType;
    }

    public String create(Class<?> clz) {
        final Field[] fields = clz.getDeclaredFields();
        StringJoiner joiner = new StringJoiner(SEPARATOR, COLUMN_OPEN_BRACKET, COLUMN_CLOSE_BRACKET);

        Arrays.stream(fields)
                .filter(this::excludeColumn)
                .forEach(field -> {
                    StringBuilder query = new StringBuilder();
                    query
                            .append(name(field))
                            .append(type(field))
                            .append(properties(field))
                            .append(generation(field))
                            .append(primaryKey(field));

                    joiner.add(query);
                });

        return joiner.toString();
    }

    private boolean excludeColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private String name(Field field) {
        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (existColumnNameProperty(columnAnnotation)) {
            return columnAnnotation.name();
        }
        return field.getName();
    }

    private static boolean existColumnNameProperty(jakarta.persistence.Column columnAnnotation) {
        return columnAnnotation != null && !columnAnnotation.name().isEmpty();
    }

    private String type(Field field) {
        return SPACE + converter.convert(field.getType());
    }

    private String properties(Field field) {
        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (existColumnNullableProperty(columnAnnotation)) {
            return NOT_NULL;
        }
        return NOTHING;
    }

    private static boolean existColumnNullableProperty(jakarta.persistence.Column columnAnnotation) {
        return columnAnnotation != null && !columnAnnotation.nullable();
    }

    private String primaryKey(Field field) {
        if (hasPrimaryKeyAnnotation(field)) {
            return PK_QUERY;
        }
        return NOTHING;
    }

    private static boolean hasPrimaryKeyAnnotation(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private String generation(Field field) {
        return generationType.value(field);
    }
}

