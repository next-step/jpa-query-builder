package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class TableInfo {
    Class<?> entity;
    public TableInfo(Class<?> entity) {
        this.entity = entity;
    }

    public String getTableName() {
        this.throwIfNotEntity();

        Table table = this.entity.getAnnotation(Table.class);
        if (table == null || table.name().isBlank()) {
            return this.entity.getSimpleName();
        }
        return table.name();
    }

    private void throwIfNotEntity() {
        if (!this.entity.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class %s is not an entity".formatted(entity.getSimpleName()));
        }
    }
}
