package persistence.sql.ddl;

import jakarta.persistence.Table;

public class TableMetadataExtractor {

    private final Class<?> type;

    public TableMetadataExtractor(Class<?> type) {
        this.type = type;
    }

    public String getTableName() {
        if (type.isAnnotationPresent(Table.class)) {
            Table table = type.getAnnotation(Table.class);
            return getTableName(type, table);
        }

        return type.getSimpleName();
    }

    private String getTableName(Class<?> type, Table table) {
        if (table.name().equals("")) {
            return type.getSimpleName();
        }

        return table.name();
    }

}
