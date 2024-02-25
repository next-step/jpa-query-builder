package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static persistence.sql.dml.parser.ValueParser.insertValuesClauseParse;
import static persistence.sql.dml.parser.ValueParser.valueParse;


public class EntityColumns {
    private final Map<String, Field> entityColumns;

    /**
     * Id 어노테이션이 붙은 컬럼이 맨앞에 위치한다.
     */
    private EntityColumns(final Class<?> clazz) {
        this.entityColumns = Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(this::idFirstOrdered))
                .filter(this::isNotTransientField)
                .collect(Collectors.toMap(this::getFieldName, Function.identity(),
                        (f1, f2) -> f1, LinkedHashMap::new));
    }

    public static EntityColumns of(Class<?> clazz) {
        return new EntityColumns(clazz);
    }

    public String names() {
        return String.join(", ", this.entityColumns.keySet());
    }

    public String insertValues(Object object, Dialect dialect) {
        return this.entityColumns.values().stream()
                .map(f -> insertValuesClauseParse(f, object, dialect))
                .collect(Collectors.joining(", "));
    }

    public String primaryFieldName() {
        return this.entityColumns.keySet().iterator().next();
    }

    public String primaryFieldValue(Object object) {
        return valueParse(this.entityColumns.values().iterator().next(), object);
    }

    private String getFieldName(final Field field) {
        if (isNotBlankOf(field)) {
            return field.getAnnotation(Column.class).name();
        }

        return field.getName();
    }

    private boolean isNotBlankOf(final Field field) {
        return field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank();
    }

    private Integer idFirstOrdered(Field field) {
        return field.isAnnotationPresent(Id.class) ? 0 : 1;
    }

    private boolean isNotTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }
}
