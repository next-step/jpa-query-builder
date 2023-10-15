package persistence.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.exception.PersistenceException;

import java.util.Optional;

public class EntityMetadata<T> {

    private final String tableName;
    private final EntityColumns columns;
    private final EntityColumn idColumn;

    public EntityMetadata(final Class<T> clazz) {
        this.validate(clazz);
        this.tableName = initTableName(clazz);
        this.columns = new EntityColumns(clazz);
        this.idColumn = this.columns.getId();
    }

    private void validate(final Class<T> clazz) {
        if(!clazz.isAnnotationPresent(Entity.class)) {
            throw new PersistenceException(clazz.getName() + "은 Entity 클래스가 아닙니다.");
        }
    }

    private String initTableName(final Class<?> clazz) {
        final Table tableAnnotation = clazz.getDeclaredAnnotation(Table.class);
        return Optional.ofNullable(tableAnnotation)
                .filter(table -> !table.name().isEmpty())
                .map(Table::name)
                .orElseGet(clazz::getSimpleName);
    }

    public String getTableName() {
        return this.tableName;
    }

    public EntityColumns getColumns() {
        return this.columns;
    }

    public String getIdColumnName() {
        return this.idColumn.getName();
    }
}
