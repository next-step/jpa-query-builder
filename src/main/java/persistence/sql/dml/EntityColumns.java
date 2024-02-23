package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dml.keygenerator.KeyGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static persistence.sql.dml.parser.ValueParser.insertValuesClauseParse;
import static persistence.sql.dml.parser.ValueParser.valueParse;

public class EntityColumns {
    private final Map<String, EntityValue> entityColumns;

    private EntityColumns(final Object object, final KeyGenerator keyGenerator) {
        this.entityColumns = Arrays.stream(object.getClass().getDeclaredFields())
                .sorted(Comparator.comparing(this::idFirstOrdered))
                .filter(this::isNotTransientField)
                .collect(Collectors.toMap(this::getFieldName,
                        f -> new EntityValue(valueParse(f, object), insertValuesClauseParse(f, object, keyGenerator)),
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static EntityColumns of(final Object object, final KeyGenerator keyGenerator) {
        return new EntityColumns(object, keyGenerator);
    }

    public String names() {
        return String.join(", ", this.entityColumns.keySet());
    }

    public String insertValues() {
        return this.entityColumns.values().stream()
                .map(EntityValue::getInsertClause)
                .collect(Collectors.joining(", "));
    }

    public String primaryFieldName() {
        return this.entityColumns.keySet().iterator().next();
    }

    public String primaryFieldValue() {
        return this.entityColumns.values().iterator().next().getValue();
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
