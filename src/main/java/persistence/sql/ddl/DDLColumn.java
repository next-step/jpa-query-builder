package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class DDLColumn {

    private static final String SPACE = " ";

    private static final Map<Class<?>, String> ddlFieldTypeString = Map.of(
            Long.class, "BIGINT",
            Integer.class, "INTEGER",
            String.class, "VARCHAR(255)"
    );

    private static final Map<GenerationType, String> ddlStrategyDDLString = Map.of(
            GenerationType.IDENTITY, "AUTO_INCREMENT"
    );

    private static final Map<Boolean, String> ddlColumnNullableString = Map.of(
            true, "NULL",
            false, "NOT NULL"
    );


    private final List<Field> fields;


    public DDLColumn(Field[] fields) {
        if (fields == null || fields.length <= 0) {
            throw new IllegalArgumentException("필드가 존재하지 않습니다.");
        }

        this.fields = new ArrayList<>(Arrays.asList(fields))
                .stream()
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }

    public String makeColumnsDDL() {
        return String.join(",", this.fields.stream().map(this::makeColumnDDL).collect(Collectors.toList()));
    }

    public String makeColumnDDL(Field field) {
        StringBuilder columnStringBuilder = new StringBuilder();
        columnStringBuilder.append(getColumnName(field));
        columnStringBuilder.append(SPACE);
        columnStringBuilder.append(getFieldType(field));

        String columnNullable = getColumnNullable(field);
        if (columnNullable != null) {
            columnStringBuilder.append(SPACE);
            columnStringBuilder.append(getColumnNullable(field));
        }


        if (field.isAnnotationPresent(Id.class)) {
            columnStringBuilder.append(SPACE);
            columnStringBuilder.append("PRIMARY KEY");
        }

        if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
            String autoIncrementAnnotationString = getStrategyDDL(annotation.strategy());
            columnStringBuilder.append(SPACE);
            columnStringBuilder.append(autoIncrementAnnotationString);
        }

        return columnStringBuilder.toString();
    }


    private String getColumnName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        String columnName = field.getAnnotation(Column.class).name();
        if (columnName.isEmpty()) {
            return field.getName();
        }

        return field.getName();
    }

    private String getColumnNullable(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return null;
        }

        if (field.isAnnotationPresent(Id.class)) {
            return null;
        }

        boolean nullable = field.getAnnotation(Column.class).nullable();

        return Optional.ofNullable(ddlColumnNullableString.get(nullable))
                .orElseThrow(() -> new IllegalArgumentException("Nullable 조건에 맞는게 있지 않습니다."));
    }

    private String getFieldType(Field field) {
        return Optional.ofNullable(ddlFieldTypeString.get(field.getType()))
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 타입이 존재하지 않습니다."));
    }

    private String getStrategyDDL(GenerationType generationType) {
        if (generationType == null) {
            throw new IllegalArgumentException("Generation Type이 존재하지 않습니다.");
        }

        return Optional.ofNullable(ddlStrategyDDLString.get(generationType))
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 Strategy 타입이 존재하지 않습니다."));
    }


}
