package persistence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityTableColumns {
    private List<EntityColumn> columns = new ArrayList<>();

    public EntityTableColumns(List<EntityColumn> columns) {
        this.columns = columns;
    }

    public EntityTableColumns() {
    }

    public void setColumns(List<EntityColumn> columns) {
        this.columns = columns;
    }

    public List<EntityColumn> getAll() {
        return columns;
    }

    public List<EntityColumn> getPrimaryColumns() {
        return columns.stream()
                .filter(EntityColumn::isPrimary)
                .collect(Collectors.toList());
    }

    public List<EntityColumn> getNonPrimaryColumns() {
        return columns.stream()
                .filter(column -> !column.isPrimary())
                .collect(Collectors.toList());
    }
}
