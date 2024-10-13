package persistence.model;

import jakarta.persistence.Table;
import persistence.model.util.ReflectionUtil;
import persistence.sql.dialect.Dialect;

import java.util.List;

public class EntityTable {
    private final String name;

    private final EntityTableColumns columns;

    private EntityTable(String name, EntityTableColumns columns) {
        this.name = name;
        this.columns = columns;
    }

    public static EntityTable build(Class<?> entityClass, Dialect dialect) {
        String name = getName(entityClass);
        EntityTableColumns entityColumns = EntityTableColumns.build(entityClass, dialect);

        return new EntityTable(name, entityColumns);
    }

    private static String getName(Class<?> entityClass) {
        return ReflectionUtil.getAnnotationIfPresent(entityClass, Table.class)
                .map(Table::name)
                .orElse(entityClass.getSimpleName());
    }

    public String getName() {
        return name;
    }

    public List<EntityColumn> getColumns() {
        return columns.getAll();
    }

    public List<EntityColumn> getPrimaryColumns() {
        return columns.getPrimaryColumns();
    }
}
