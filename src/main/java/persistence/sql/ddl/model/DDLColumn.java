package persistence.sql.ddl.model;

import persistence.sql.ColumnUtils;
import persistence.sql.ddl.converter.TypeConverter;
import persistence.sql.ddl.mapping.PrimaryKeyGenerationType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static persistence.sql.ColumnUtils.name;

public class DDLColumn {

    private static final String PK_QUERY = " NOT NULL PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";
    private static final String SEPARATOR = ", ";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private final TypeConverter converter;
    private final PrimaryKeyGenerationType generationType;

    public DDLColumn(TypeConverter converter, PrimaryKeyGenerationType generationType) {
        this.converter = converter;
        this.generationType = generationType;
    }

    public String create(Class<?> clz) {
        final Field[] fields = clz.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(ColumnUtils::includeColumn)
                .map(this::createQuery)
                .collect(Collectors.joining(SEPARATOR));
    }

    private String createQuery(Field field) {
        return new StringBuilder()
                .append(name(field))
                .append(type(field))
                .append(properties(field))
                .append(primaryKeyGenerationTypeValue(field))
                .append(primaryKey(field))
                .toString();
    }

    private String type(Field field) {
        return SPACE + converter.convert(field.getType());
    }

    private String properties(Field field) {
        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (isTrueColumnNullableProperty(columnAnnotation)) {
            return NOT_NULL;
        }
        return EMPTY;
    }

    private String primaryKeyGenerationTypeValue(Field field) {
        return generationType.value(field);
    }

    private String primaryKey(Field field) {
        if (ColumnUtils.isId(field)) {
            return PK_QUERY;
        }
        return EMPTY;
    }

    private static boolean isTrueColumnNullableProperty(jakarta.persistence.Column columnAnnotation) {
        return columnAnnotation != null && !columnAnnotation.nullable();
    }
}
