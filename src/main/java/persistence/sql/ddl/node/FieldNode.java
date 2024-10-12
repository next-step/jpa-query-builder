package persistence.sql.ddl.node;

import jakarta.persistence.Id;
import persistence.sql.ddl.Types;

import java.lang.reflect.Field;

public class FieldNode implements SQLNode{
    private final Field field;

    public FieldNode(Field field) {
        this.field = field;
    }

    public static FieldNode from(Field field) {
        return new FieldNode(field);
    }

    public boolean isPrimaryKey() {
        return field.isAnnotationPresent(Id.class);
    }

    public String getFieldName() {
        return field.getName();
    }

    public Types getFieldType() {
        return Types.findByType(field.getType());
    }
}
