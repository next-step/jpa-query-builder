package persistence.sql.meta;

import jakarta.persistence.*;
import persistence.sql.meta.EntityType.H2ColumnType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnInfo {
    private String name;
    private H2ColumnType columnType;
    private boolean primaryKey;
    private boolean transientAnnotaion;
    private List<String> options;

    private ColumnInfo(String name, H2ColumnType columnType, boolean primaryKey, boolean transientAnnotation, List<String> options) {
        this.name = name;
        this.columnType = columnType;
        this.primaryKey = primaryKey;
        this.transientAnnotaion = transientAnnotation;
        this.options = options;
    }

    public static ColumnInfo extract(Field field) {
        return new ColumnInfo(
                extractColumnName(field),
                H2ColumnType.of(field.getType()),
                field.isAnnotationPresent(Id.class),
                field.isAnnotationPresent(Transient.class),
                extractOptions(field)
        );
    }

    private static List<String> extractOptions(Field field){
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

    private static String extractColumnName(Field field){
        final var columnAnnotation = field.getAnnotation(Column.class);
        return Objects.isNull(columnAnnotation) || columnAnnotation.name().isBlank() ? field.getName() : columnAnnotation.name();
    }
    public String getName() {
        return name;
    }

    public H2ColumnType getColumnType() {
        return columnType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }
    public List<String> getOptions() {
        return options.stream().distinct().collect(Collectors.toList());
    }

    public boolean isNotTransient() {
        return !transientAnnotaion;
    }
}
