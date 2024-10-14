package persistence.sql.ddl.create.component.column;

import persistence.sql.ddl.create.component.ComponentBuilder;

import java.lang.reflect.Field;

public class ColumnComponentBuilder implements ComponentBuilder {
    private static final String INDENT = "\t";
    private final StringBuilder componentBuilder = new StringBuilder(INDENT);

    public static ColumnComponentBuilder of(Field field) {
        return new ColumnComponentBuilder(field);
    }

    public ColumnComponentBuilder(Field field) {
        this.componentBuilder
                .append(field.getName()).append(INDENT)
                .append(DataTypeConverter.convert(field.getType())).append(INDENT);
    }

    /* TODO : append comment, nullable or not, etc. */

    public StringBuilder getComponentBuilder() {
        return this.componentBuilder;
    }
}
