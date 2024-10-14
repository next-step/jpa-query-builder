package persistence.sql.ddl.create.component.column;

import java.lang.reflect.Field;

public class ColumnComponentBuilder {
    private static final String INDENT = "\t";
    private static final String COMMA_NEW_LINE = ",\n";
    private final StringBuilder componentBuilder = new StringBuilder();

    private ColumnComponentBuilder() {
    }

    public static ColumnComponentBuilder from(Field field) {
        ColumnComponentBuilder columnComponentBuilder = new ColumnComponentBuilder();
        columnComponentBuilder.componentBuilder
                .append(INDENT)
                .append(field.getName()).append(INDENT)
                .append(DataTypeConverter.convert(field.getType())).append(COMMA_NEW_LINE);
        return columnComponentBuilder;
    }

    /* TODO : append comment, nullable or not, etc. */

    public StringBuilder getComponentBuilder() {
        return this.componentBuilder;
    }
}
