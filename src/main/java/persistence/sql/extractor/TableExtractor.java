package persistence.sql.extractor;


import jakarta.persistence.Table;

public class TableExtractor {
    private final Class<?> entityClazz;

    public TableExtractor(Class<?> entityClazz) {
        this.entityClazz = entityClazz;
    }

    public TableData createTable() {
        return new TableData(getName());
    }

    public String getName() {
        Table table = entityClazz.getAnnotation(Table.class);
        if (table == null) {
            return entityClazz.getSimpleName().toLowerCase();
        }
        return table.name();
    }
}
