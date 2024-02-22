package persistence.inspector;

import java.util.List;
import java.util.stream.Collectors;

public class EntityTable {
    private final Class<?> clazz;
    private final List<EntityField> fields;

    public static EntityTable from(Class<?> clazz) {
        return new EntityTable(clazz);
    }

    public EntityTable(Class<?> clazz) {
        this.clazz = clazz;
        this.fields = buildEntityFields();
    }

    private List<EntityField> buildEntityFields() {
        return EntityMetadataInspector.getFields(clazz).stream()
            .filter(EntityMetadataInspector::isPersistable)
            .map(EntityField::new)
            .collect(Collectors.toList());
    }

    public String getTableName() {
        return EntityMetadataInspector.getTableName(clazz);
    }

    private EntityField getGetFieldByColumnName(String columnName) {
        return this.fields.stream().filter(field -> field.getColumnName().equals(columnName))
            .findFirst().orElse(null);
    }

    private EntityField getPrimaryKeyField() {
        return this.fields.stream().filter(EntityField::isPrimaryKey).findFirst().orElse(null);
    }


}
