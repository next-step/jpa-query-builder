package persistence.common;

import persistence.annotations.*;

import java.lang.reflect.Field;
import java.util.Optional;

public class FieldClazz {

    private String name;
    private Class<?> clazz;
    private boolean isPk;
    private GenerationType generationType;
    private boolean nullable;
    private boolean isTransient;

    public FieldClazz(Field field) {
        this.clazz = field.getType();
        this.name = Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElse("");
        if (this.name.equals("")) {
            this.name = field.getName();
        }
        this.nullable = Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::nullable)
                .orElse(true);
        this.isPk = field.isAnnotationPresent(Id.class);
        this.generationType = Optional.ofNullable(field.getAnnotation(GeneratedValue.class))
                .map(GeneratedValue::strategy)
                .orElse(null);
        this.isTransient = field.isAnnotationPresent(Transient.class);
    }

    public boolean isPk() {
        return isPk;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public boolean isNullable() {
        return nullable;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public boolean notTransient() {
        return !isTransient;
    }
}
