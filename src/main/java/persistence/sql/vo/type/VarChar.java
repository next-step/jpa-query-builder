package persistence.sql.vo.type;

import java.lang.reflect.Field;

public class VarChar implements DatabaseType {
    private final Field field;

    public VarChar(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "varchar(300)";
    }
}
