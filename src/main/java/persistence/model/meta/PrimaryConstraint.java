package persistence.model.meta;

import persistence.model.EntityColumn;
import persistence.model.EntityTableColumns;

import java.util.List;

public class PrimaryConstraint extends Constraint {
    protected PrimaryConstraint(List<EntityColumn> columns) {
        super(columns);
    }

    public static PrimaryConstraint build(EntityTableColumns entityColumns) {
        List<EntityColumn> primaryColumns = entityColumns.getPrimaryColumns();
        return new PrimaryConstraint(primaryColumns);
    }
}
