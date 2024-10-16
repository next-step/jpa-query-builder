package persistence.sql.ddl.create.component.column;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.create.component.ComponentUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnComponentBuilder {
    private String name;
    private String type;
    private final List<String> options = new ArrayList<>();

    private static final String INDENT = "\t";


    private ColumnComponentBuilder() {
    }

    public static ColumnComponentBuilder from(Field field) {
        ColumnComponentBuilder columnComponentBuilder = new ColumnComponentBuilder();
        columnComponentBuilder.setName(field);
        columnComponentBuilder.setType(field);
        columnComponentBuilder.setOptions(field);

        return columnComponentBuilder;
    }

    private void setName(Field field) {
        this.name = ComponentUtils.getNameFromField(field);
    }

    private void setType(Field field) {
        this.type = ColumnDataType.convert(field.getType());
    }

    private void setOptions(Field field) {
        addOptionIfIdAutoIncrement(field);
        addOptionIfNotNull(field);
        /* TODO : options */
    }

    private void addOptionIfIdAutoIncrement(Field field) {
        if (field.isAnnotationPresent(Id.class)
                && field.isAnnotationPresent(GeneratedValue.class)
                && GenerationType.IDENTITY.equals(field.getAnnotation(GeneratedValue.class).strategy())) {
            this.options.add("auto_increment");
        }
    }

    private void addOptionIfNotNull(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !field.getAnnotation(Column.class).nullable()) {
            this.options.add("not null");
        }
    }

    /* TODO : options (ex.comment, etc.) */

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.name).append(INDENT)
                .append(this.type).append(INDENT);
        this.options.forEach(option -> stringBuilder.append(option).append(INDENT));

        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
