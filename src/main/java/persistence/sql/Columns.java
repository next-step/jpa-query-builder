package persistence.sql;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Columns {
    protected final Map<String, Field> columns = new LinkedHashMap<>();

    public void put(String columnName, Field field) {
        columns.put(columnName, field);
    }

    public Set<String> getColumnNames() {
        return columns.keySet();
    }

    public Collection<Field> getColumnFields() {
        return columns.values();
    }

    public boolean isNotEmpty() {
        return !columns.isEmpty();
    }

    public Map<String, Field> get() {
        return columns;
    }
}
