package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ColumnValues<T> {

    private final List<ColumnValue> columnValues;

    public ColumnValues(T entity) throws IllegalAccessException {
        this.columnValues = getColumnValues(entity);
    }

    public List<ColumnValue> getValues() {
        return new ArrayList<>(columnValues);
    }

    private List<ColumnValue> getColumnValues(T entity) throws IllegalAccessException {
        List<ColumnValue> columnValues = new LinkedList<>();

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                continue;
            }

            Object value = field.get(entity);
            columnValues.add(new ColumnValue(field, value));
        }
        return columnValues;
    }

}
