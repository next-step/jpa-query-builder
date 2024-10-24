package persistence.sql;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class TableMeta {
    private final String tableName;
    private final TableId tableId;
    private final List<TableColumn> tableColumns;

    public TableMeta(Class<?> entityClass) {
        if(isNotEntity(entityClass)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }
        this.tableName = tableName(entityClass);
        this.tableColumns = setColumns(entityClass);
        this.tableId = tableId(entityClass);
    }

    public String tableName() {
        return tableName;
    }

    public List<TableColumn> tableColumn() {
        return tableColumns;
    }

    public TableId tableId() {
        return tableId;
    }

    private boolean isNotEntity(Class<?> entityClass) {
        return !entityClass.isAnnotationPresent(Entity.class);
    }

    private String tableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return !table.name().isEmpty() ? table.name() : entityClass.getSimpleName();
        }
        return entityClass.getSimpleName();
    }

    private TableId tableId(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(TableId::new)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Entity must have a field annotated with @Id"));
    }

    private List<TableColumn> setColumns(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::isPersistentField)
                .map(TableColumn::new).toList();
    }

    private boolean isPersistentField(Field field) {
        return !(field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class))
                && !field.isAnnotationPresent(Transient.class);
    }
}
