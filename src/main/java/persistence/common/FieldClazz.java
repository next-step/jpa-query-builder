package persistence.common;

import persistence.annotations.*;

import java.lang.reflect.Field;
import java.util.Optional;

public class FieldClazz {

    private Field field;
    private String name;
    private Class<?> clazz;
    private boolean isId;
    private GenerationType generationType;
    private boolean nullable;
    private boolean isTransient;

    public FieldClazz(Field field) {
        this.field = field;
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
        this.isId = field.isAnnotationPresent(Id.class);
        this.generationType = Optional.ofNullable(field.getAnnotation(GeneratedValue.class))
                .map(GeneratedValue::strategy)
                .orElse(null);
        this.isTransient = field.isAnnotationPresent(Transient.class);

        this.field.setAccessible(true);
    }

    public boolean isId() {
        return isId;
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

    public Field getField() {
        return field;
    }

    public Object get(Object entity){
        try {
            return this.field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void set(T entity, Object value) {
        try {
            this.field.setAccessible(true);
            this.field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
