package persistence.sql.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Columns {

    private final List<Column> columns;

    public Columns(Field[] fields) {
        this.columns = buildColumns(fields);
    }

    private List<Column> buildColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !hasIdAnnotation(field))
                .filter(field -> !hasTransientAnnotation(field))
                .map(Column::new)
                .collect(Collectors.toList());
    }

    private boolean hasIdAnnotation(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean hasTransientAnnotation(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        columns.forEach(column -> {
            String columnName = column.getName();
            columnNames.add(columnName);
        });
        return Collections.unmodifiableList(columnNames);
    }

    public Stream<Column> stream() {
        return columns.stream();
    }
}
