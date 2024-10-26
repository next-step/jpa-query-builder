package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import persistence.sql.ColumnName;

import java.lang.reflect.Field;

public final class DmlColumn {
    private final ColumnName name;
    private final DmlColumnValue value;
    private final boolean generatedValue;

    private DmlColumn(ColumnName name, DmlColumnValue value, boolean generatedValue) {
        this.name = name;
        this.value = value;
        this.generatedValue = generatedValue;
    }

    public static DmlColumn from(Object o, Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return new DmlColumn(ColumnName.from(field), new DmlColumnValue(getFieldValue(o, field)), false);
        }
        return new DmlColumn(ColumnName.from(field), new DmlColumnValue(getFieldValue(o, field)), true);
    }

    private static Object getFieldValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("접근할 수 없는 필드입니다: " + field.getName());
        }
    }

    public String getName() {
        return name.value();
    }

    public String getStringValue() {
        return value.toString();
    }

    public boolean notGeneratedValue() {
        return !generatedValue;
    }
}
