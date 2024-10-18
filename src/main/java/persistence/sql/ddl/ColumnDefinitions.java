package persistence.sql.ddl;

import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnDefinitions {

    private final Map<ColumnName, ColumnType> columnDefinitions;

    public ColumnDefinitions(Class<?> clazz) {
        this.columnDefinitions = getColumnDefinitions(clazz);
    }

    public Map<ColumnName, ColumnType> getColumnDefinitions() {
        return columnDefinitions;
    }

    private Map<ColumnName, ColumnType> getColumnDefinitions(Class<?> entityClass) {
        Map<ColumnName, ColumnType> columnDefinitions = new LinkedHashMap<>();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }

            ColumnName columnName = new ColumnName(field);
            ColumnType columnType = new ColumnType(field);

            columnDefinitions.put(columnName, columnType);
        }
        return columnDefinitions;
    }

}
