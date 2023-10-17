package persistence.sql.ddl.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Columns {
    final private Map<String, Column> columns;

    public Columns(final Class<?> entity) {
        this.columns = generateColumns(entity);
    }

    public Columns(final Map<String, Column> columns) {
        this.columns = columns;
    }

    private Map<String, Column> generateColumns(final Class<?> entity) {
        Map<String, Column> columns = new HashMap<>();
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            Column column = new Column(field);
            columns.put(column.getName(), column);
        }
        return columns;
    }


    public Column getColumn(String name) {
        return columns.get(name);
    }

    public Columns getIdColumns() {
        return new Columns(columns.values().stream()
                .filter(Column::isPrimaryKey)
                .collect(toMap(Column::getName, column -> column)));
    }

}
