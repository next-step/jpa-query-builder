package persistence.model;

import jakarta.persistence.Table;
import persistence.model.meta.Constraint;
import persistence.model.meta.PrimaryConstraint;
import persistence.model.util.ReflectionUtil;

import java.util.List;

public class EntityTable {
    private final String name;

    private final EntityTableColumns columns;

    private final Constraint primaryConstraint;

    private EntityTable(String name, EntityTableColumns columns, Constraint primaryConstraint) {
        this.name = name;
        this.columns = columns;
        this.primaryConstraint = primaryConstraint;
    }

    public static EntityTable build(Class<?> entityClass) {
        String name = getName(entityClass);
        EntityTableColumns entityColumns = EntityTableColumns.build(entityClass);
        Constraint primaryConstraint = PrimaryConstraint.build(entityColumns);

        return new EntityTable(name, entityColumns, primaryConstraint);
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

    public Constraint getPrimaryConstraint() {
        return primaryConstraint;
    }
}
