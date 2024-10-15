package persistence.sql.ddl;

import jakarta.persistence.*;
import org.h2.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnInfo {
    private String name;
    private JPAColumnType columnType;
    private boolean primaryKey;
    private boolean transientAnnotaion;
    private List<String> options;

    private ColumnInfo(String name, JPAColumnType columnType, boolean primaryKey, boolean transientAnnotation, List<String> options) {
        this.name = name;
        this.columnType = columnType;
        this.primaryKey = primaryKey;
        this.transientAnnotaion = transientAnnotation;
        this.options = options;
    }

    public static ColumnInfo extract(Field field) {
        return new ColumnInfo(
                extractColumnName(field),
                JPAColumnType.of(field.getType()),
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

    public JPAColumnType getColumnType() {
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

    public String generateColumnDdlQuery() {
        String columnQuery = String.join(" ", name, columnType.getQueryDefinition());
        String optionQuery = options.stream().collect(Collectors.joining(" "));
        if(StringUtils.isNullOrEmpty(optionQuery)) {
            return columnQuery;
        }
        return String.join(" ", columnQuery, optionQuery);
    }
}
