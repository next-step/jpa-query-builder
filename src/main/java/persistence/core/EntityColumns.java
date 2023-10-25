package persistence.core;

import persistence.exception.NotFoundEntityException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityColumns {

    private final Set<EntityColumn> columns;

    public EntityColumns(Collection<EntityColumn> columns, EntityColumn primaryKeyColumn) {
        assert columns != null;
        columns.remove(primaryKeyColumn);
        this.columns = new LinkedHashSet<>(columns);
    }

    public List<String> getColumnNames() {
        return this.columns.stream()
                .filter(column -> !column.hasTransient())
                .map(EntityColumn::getName)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<EntityColumn> get() {
        return this.columns.stream()
                .filter(column -> !column.hasTransient())
                .collect(Collectors.toUnmodifiableList());
    }

    public EntityColumn findColumnByField(Field field) {
        return columns.stream()
                .filter(it -> it.isEqualField(field))
                .findFirst()
                .orElseThrow(() -> new NotFoundEntityException("not found entity column: " + field.getName()));
    }
}
