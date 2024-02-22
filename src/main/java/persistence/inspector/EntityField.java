package persistence.inspector;

import java.lang.reflect.Field;

public class EntityField {

    private final Field field;

    public EntityField(Field field) {
        this.field = field;
    }

    public String getColumnName() {
        return EntityMetadataInspector.getColumnName(field);
    }

    public EntityColumnType getColumnType() {
        return EntityMetadataInspector.getColumnType(field);
    }

    public boolean isPrimaryKey() {
        return EntityMetadataInspector.isPrimaryKey(field);
    }

    public boolean isNullable() {
        return EntityMetadataInspector.isNullable(field);
    }

    public boolean isAutoIncrement() {
        return EntityMetadataInspector.isAutoIncrement(field);
    }

}
