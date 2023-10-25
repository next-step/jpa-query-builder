package persistence.sql.metadata;

import jakarta.persistence.Entity;
import persistence.dialect.Dialect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadata {
    private final Table table;

    private final Columns columns;

    public EntityMetadata(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }
        this.table = new Table(clazz);
        this.columns = new Columns(convertClassToColumnList(clazz));
    }

    private List<Column> convertClassToColumnList(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Column::new)
                .collect(Collectors.toList());
    }

    public String getTableName() {
        return table.getName();
    }

    public String getColumnsToCreate(Dialect dialect) {
        return columns.buildColumnsToCreate(dialect);
    }
}
