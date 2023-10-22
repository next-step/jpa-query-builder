package persistence.sql.meta;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.util.StringUtils;

public class EntityMeta {

    private final Class<?> clazz;
    private final ColumnMetas columnMetas;

    private EntityMeta(Class<?> clazz, ColumnMetas columnMetas) {
        this.clazz = clazz;
        this.columnMetas = columnMetas;
    }

    public static EntityMeta of(Class<?> clazz) {
        return new EntityMeta(clazz, ColumnMetas.of(clazz.getDeclaredFields()));
    }

    public boolean isEntity() {
        return clazz.isAnnotationPresent(Entity.class);
    }

    public String getTableName() {
        Table tableAnnotation = clazz.getDeclaredAnnotation(Table.class);
        if (tableAnnotation != null && !StringUtils.isNullOrEmpty(tableAnnotation.name())) {
            return tableAnnotation.name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public ColumnMetas getColumnMetas() {
        return columnMetas;
    }
}
