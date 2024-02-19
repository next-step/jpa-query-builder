package persistence.sql.ddl;

import java.lang.reflect.Field;

public class Column {
    private final Field field;

    public Column(final Field field) {
        this.field = field;
    }

    public String getColumnName() {
        return field.getName();
    }


}
