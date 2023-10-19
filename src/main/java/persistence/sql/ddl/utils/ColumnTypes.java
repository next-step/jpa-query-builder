package persistence.sql.ddl.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ColumnTypes {
    final private Map<String, ColumnType> columnTypeMap;

    public ColumnTypes(final Class<?> entity) {
        this.columnTypeMap = generateColumns(entity);
    }

    public ColumnTypes(final Map<String, ColumnType> columns) {
        this.columnTypeMap = columns;
    }

    private Map<String, ColumnType> generateColumns(final Class<?> entity) {
        Map<String, ColumnType> columns = new HashMap<>();
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            ColumnType columnType = new ColumnType(field);
            columns.put(columnType.getName(), columnType);
        }
        return columns;
    }

    public ColumnType getColumn(String name) {
        return columnTypeMap.get(name);
    }

    public ColumnTypes getIdColumns() {
        return new ColumnTypes(columnTypeMap.values().stream()
                .filter(ColumnType::isPrimaryKey)
                .collect(toMap(ColumnType::getName, columnType -> columnType)));
    }

    public List<ColumnType> getColumns() {
        return new ArrayList<>(columnTypeMap.values());
    }

}
