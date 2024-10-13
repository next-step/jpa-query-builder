package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnInfo {

    private String name;
    private ColumnType columnType;
    private boolean primary;

    private boolean trans;
    private List<String> options;

    public ColumnInfo(String name, ColumnType columnType, boolean primary, boolean trans, List<String> options) {
        this.name = name;
        this.columnType = columnType;
        this.primary = primary;
        this.trans = trans;
        this.options = options;
    }

    public static ColumnInfo extract(Field field) {
        return new ColumnInfo(
                extractColumnName(field),
                ColumnType.of(field.getType()),
                field.isAnnotationPresent(Id.class),
                field.isAnnotationPresent(Transient.class),
                extractOptions(field)
        );
    }

    private static List<String> extractOptions(Field field){
        List<String> options = new ArrayList<>();
        final var columnAnotation = field.getAnnotation(Column.class);

        if(field.isAnnotationPresent(Id.class)) {options.add("not null");}

        if(!Objects.isNull(columnAnotation) && !columnAnotation.nullable()) {options.add("not null");}

        final var generatedValue = field.getAnnotation(GeneratedValue.class);
        if(!Objects.isNull(generatedValue) && generatedValue.strategy().equals(GenerationType.IDENTITY)){
            options.add("auto_increment");
        }

        return options;
    }

    private static String extractColumnName(Field field){
        final var columnAnotation = field.getAnnotation(Column.class);
        return Objects.isNull(columnAnotation) || columnAnotation.name().isBlank() ? field.getName() : columnAnotation.name();
    }
    public String getName() {
        return name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public boolean isPrimary() {
        return primary;
    }
    public List<String> getOptions() {
        return options.stream().distinct().collect(Collectors.toList());
    }

    public boolean isTrans() {
        return trans;
    }
}
