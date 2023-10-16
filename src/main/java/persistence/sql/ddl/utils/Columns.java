package persistence.sql.ddl.utils;

import java.lang.reflect.Field;
import java.util.*;

public class Columns {
    private Map<String, Column> columns;

    public Columns(final Class<?> entity) {
        this.columns = generateColumns(entity);
    }
    private Map<String, Column> generateColumns(final Class<?> entity) {
        Map<String, Column> columns = new HashMap<>();
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            Column column = new Column();
            column.setName(field.getName());
            columns.put(column.getName(), column);
        }
        return columns;
    }

    public Column getColumn(String name) {
        return columns.get(name);
    }
}
