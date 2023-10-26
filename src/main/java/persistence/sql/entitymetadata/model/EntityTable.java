package persistence.sql.entitymetadata.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

public class EntityTable<E> extends EntityValidatable<E> {
    private String name;
    private EntityColumns<E> columns;
    private GenerationType idGenerationType;

    public EntityTable(Class<E> entityClass) {
        super(entityClass);
        this.name = createTableName();
        this.columns = createColumns();
        this.idGenerationType = createIdGenerationType();
    }

    private GenerationType createIdGenerationType() {
        return entityColumnFields.stream()
                .filter(field -> field.isAnnotationPresent(GeneratedValue.class))
                .map(field -> field.getAnnotation(GeneratedValue.class).strategy())
                .findFirst()
                .orElse(GenerationType.AUTO);
    }

    private EntityColumns<E> createColumns() {
        return new EntityColumns<>(entityClass);
    }

    private String createTableName() {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return createTableName(entityClass, entityClass.getAnnotation(Table.class));
        }

        return entityClass.getSimpleName();
    }

    private String createTableName(Class<E> entityClass, Table table) {
        String tableAnnotationName = table.name();

        if ("".equals(tableAnnotationName)) {
            return entityClass.getSimpleName();
        }

        return tableAnnotationName;
    }

    public String getName() {
        return name;
    }

    public EntityColumns<E> getColumns() {
        return columns;
    }

    public EntityColumn<E, ?> getIdColumn() {
        return columns.getIdColumn();
    }
}
