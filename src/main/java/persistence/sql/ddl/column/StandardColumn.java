package persistence.sql.ddl.column;

import java.lang.reflect.Field;

public class StandardColumn implements EntityColumn {

    private final Column column;

    private StandardColumn(Column column) {
        this.column = column;
    }

    public static EntityColumn from(Field field) {
        Column column = Column.from(field);

        return new StandardColumn(column);
    }

    @Override
    public String defineColumn() {
        return column.getName() +
                BLANK +
                column.getType() +
                column.getLengthDefinition() +
                BLANK +
                column.getNullableDefinition();
    }

    @Override
    public String getName() {
        return column.getName();
    }
}
