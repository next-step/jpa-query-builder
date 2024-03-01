package persistence.sql.ddl.column;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class ColumnConvertor {

    private ColumnConvertor() {
    }

    public static EntityColumn convert(Field field) {
        Id id = field.getAnnotation(Id.class);

        if (id != null) {
            return IdColumn.from(field);
        }

        return StandardColumn.from(field);
    }
}
