package persistence.model;

import persistence.model.meta.Constraint;
import persistence.model.meta.PrimaryConstraint;

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
        String name = entityClass.getSimpleName();
        EntityTableColumns entityColumns = EntityTableColumns.build(entityClass);
        Constraint primaryConstraint = PrimaryConstraint.build(entityColumns);

        return new EntityTable(name, entityColumns, primaryConstraint);
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
