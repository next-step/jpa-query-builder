package persistence.sql.ddl.utils;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.lang.reflect.Field;
import java.util.Optional;

public class ColumnField implements ColumnType2 {

    private final String name;
    private final boolean nullable;

    private final boolean isTransient;
    private final int length;

    public ColumnField(final Field field) {
        this.name = parseName(field);
        this.nullable = parsNullable(field);
        this.isTransient = parseTransient(field);
        this.length = parseLength(field);
    }


    private boolean parsNullable(final Field field) {
       return Optional.ofNullable(getColumnAnnotation(field))
                .map(Column::nullable)
                .orElse(true);
    }

    private boolean parseTransient(final Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String parseName(Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .filter(column -> !column.name().isEmpty())
                .map(Column::name)
                .orElse(field.getName());
    }

    private int parseLength(final Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .map(Column::length)
                .orElse(255);
    }

    private Column getColumnAnnotation(final Field field) {
        return field.getAnnotation(Column.class);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isId() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return this.nullable;
    }

    @Override
    public boolean isTransient() {
        return this.isTransient;
    }

    @Override
    public int getLength() {
        return this.length;
    }


}
