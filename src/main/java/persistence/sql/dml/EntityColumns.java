package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dml.keygenerator.KeyGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static persistence.sql.dml.parser.ValueParser.insertValuesClauseParse;

public class EntityColumns {
    private final Object object;

    private EntityColumns(final Object object) {
        this.object = object;
    }

    public static EntityColumns of(final Object object) {
        return new EntityColumns(object);
    }

    public String names() {
        return Arrays.stream(this.object.getClass().getDeclaredFields())
                .sorted(Comparator.comparing(this::idFirstOrdered))
                .filter(this::isNotTransientField)
                .map(this::getFieldName)
                .collect(Collectors.joining(", "));
    }

    public String values(KeyGenerator keyGenerator) {
        return Arrays.stream(this.object.getClass().getDeclaredFields())
                .sorted(Comparator.comparing(this::idFirstOrdered))
                .filter(this::isNotTransientField)
                .map(f -> insertValuesClauseParse(f, object, keyGenerator))
                .collect(Collectors.joining(", "));
    }

    private String getFieldName(final Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank()) {
            return field.getAnnotation(Column.class).name();
        }

        return field.getName();
    }

    private Integer idFirstOrdered(Field field) {
        return field.isAnnotationPresent(Id.class) ? 0 : 1;
    }

    private boolean isNotTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }
}
