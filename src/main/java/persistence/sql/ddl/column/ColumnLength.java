package persistence.sql.ddl.column;

import java.lang.reflect.Field;

public class ColumnLength {

    public static final int DEFAULT_LENGTH = 255;

    private final int length;

    private ColumnLength(int length) {
        this.length = length;
    }

    public static ColumnLength from(Field field) {
        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);

        if (column == null) {
            return new ColumnLength(DEFAULT_LENGTH);
        }

        return new ColumnLength(column.length());
    }

    public int getLength() {
        return length;
    }
}
