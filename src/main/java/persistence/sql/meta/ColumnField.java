package persistence.sql.meta;

import jakarta.persistence.*;
import persistence.sql.meta.entityType.H2ColumnType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnField {
    private final Field field;

    public ColumnField(Field field) {
        this.field = field;
    }

    private List<String> extractOptions(){
        List<String> options = new ArrayList<>();
        final var columnAnotation = field.getAnnotation(Column.class);

        if(field.isAnnotationPresent(Id.class)) {
            options.add("not null");
        }

        if(!Objects.isNull(columnAnotation) && !columnAnotation.nullable()) {
            options.add("not null");
        }

        // TODO : 전략패턴 사용해서 GeneratedValue 재구현
        final var generatedValue = field.getAnnotation(GeneratedValue.class);
        if(!Objects.isNull(generatedValue) && generatedValue.strategy().equals(GenerationType.IDENTITY)){
            options.add("auto_increment");
        }

        return options;
    }

    public String getName(){
        final var columnAnnotation = field.getAnnotation(Column.class);
        return Objects.isNull(columnAnnotation) || columnAnnotation.name().isBlank() ? field.getName() : columnAnnotation.name();
    }

    public H2ColumnType getColumnType() {
        return H2ColumnType.of(field.getType());
    }

    public boolean isPrimaryKey() {
        return field.isAnnotationPresent(Id.class);
    }
    public List<String> getOptions() {
        return extractOptions().stream().distinct().collect(Collectors.toList());
    }

    public boolean isNotTransient() {
        return !field.isAnnotationPresent(Transient.class);
    }

    public String generateWhereClause(Object object) throws Exception {
        field.setAccessible(true);
        return String.format("%s=%s", getName(), field.get(object));
    }
    public Field getField() {
        return field;
    }
}
