package persistence.sql.meta;

import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Columns {

    private static final String ID_NOT_FOUND_MESSAGE =  "Id 필드가 존재하지 않습니다.";
    private List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public static Columns from(Field[] fields) {
        return new Columns(Arrays.stream(fields)
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(Column::from)
            .collect(Collectors.toList()));
    }

    public long getIdCount() {
        return columns.stream()
            .filter(Column::isIdAnnotation)
            .count();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Column getIdColumn() {
        return columns.stream()
            .filter(Column::isIdAnnotation)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ID_NOT_FOUND_MESSAGE));
    }

    public Object getIdValue(Object entity) {
        return getIdColumn().getFieldValue(entity);
    }
}
