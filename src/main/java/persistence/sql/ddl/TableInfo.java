package persistence.sql.ddl;

import jakarta.persistence.Table;

public class TableInfo {

    private final String name;

    public TableInfo(Class<?> type) {
        if (type.isAnnotationPresent(Table.class)) {
            Table table = type.getAnnotation(Table.class);
            name = getTableName(type, table);
            return;
        }

        name = type.getSimpleName();
    }

    private String getTableName(Class<?> type, Table table) {
        if (table.name().equals("")) {
            return type.getSimpleName();
        }

        return table.name();
    }

    public String getTableName() {
        return name;
    }

}
