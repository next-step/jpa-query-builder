package persistence.sql.ddl.value;

import persistence.sql.ddl.Id;
import persistence.sql.ddl.Table;
import persistence.sql.ddl.column.Column;

import java.lang.reflect.Field;

public class ValueClause {
    private final String value;
    public ValueClause(Field field, Object object) {
        try {
            field.setAccessible(true);
            this.value = String.valueOf(field.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("value 생성에 실패하였습니다.", e);
        }
    }

    public String getQuery() {
        return this.value;
    }
}
