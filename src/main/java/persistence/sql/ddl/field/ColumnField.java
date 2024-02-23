package persistence.sql.ddl.field;

import java.lang.reflect.Field;

public class ColumnField implements QueryField {

    private final Field field;

    public ColumnField(Field field) {
        this.field = field;
    }

    public static boolean isMappableField(Field field) {
        return !IdField.isMappableField(field);
    }

    @Override
    public String toSQL() {
        return String.join(" ",
                field.getName(),
                QueryDataType.from(field.getType())
        );
    }

}
