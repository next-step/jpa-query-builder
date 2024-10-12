package persistence.sql.ddl.node;

import java.lang.reflect.Field;

public class FieldNode implements SQLNode{
    private Field field;

    public FieldNode(Field field) {
        this.field = field;
    }

    public static FieldNode from(Field field) {
        return new FieldNode(field);
    }
}
