package persistence.model.meta;

import persistence.model.EntityColumn;
import persistence.sql.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Constraint {
    private final String name = "";

    private final List<EntityColumn> columns;

    protected Constraint(List<EntityColumn> columns) {
        this.columns = columns;
    }

    public final String getName() {
        return name;
    }

    public final List<EntityColumn> getColumns() {
        return columns;
    }

    public List<String> getColumnNames() {
        return getColumns().stream()
                .map(EntityColumn::getName)
                .collect(Collectors.toList());
    }
}
