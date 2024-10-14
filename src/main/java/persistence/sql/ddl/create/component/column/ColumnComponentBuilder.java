package persistence.sql.ddl.create.component.column;

import java.lang.reflect.Field;

public class ColumnComponentBuilder {
    private static final String INDENT = "\t";
    private static final String COMMA_NEW_LINE = ",\n";
    private final StringBuilder componentBuilder = new StringBuilder(INDENT);

    private ColumnComponentBuilder(Field field) {
        this.componentBuilder
                .append(field.getName()).append(INDENT)
                .append(DataTypeConverter.convert(field.getType())).append(COMMA_NEW_LINE);
    }

    public static ColumnComponentBuilder from(Field field) {
        return new ColumnComponentBuilder(field);
    }

    /* TODO : append comment, nullable or not, etc. */

    public StringBuilder getComponentBuilder() {
        return this.componentBuilder;
    }
}
