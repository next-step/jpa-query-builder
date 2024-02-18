package persistence.sql.ddl.wrapper;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import org.h2.util.StringUtils;

public class Column {

    private final Field field;

    private Column(Field field) {
        this.field = field;
    }

    public static Column from(Field field) {
        return new Column(field);
    }

    public String getColumnName() {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);
        if (column == null || StringUtils.isNullOrEmpty(column.name())) {
            return utils.StringUtils.convertCamelToSnakeString(field.getName());
        }
        return column.name();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public boolean isIdAnnotation() {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isGeneratedValueAnnotation() {
        jakarta.persistence.GeneratedValue generatedValue = field.getDeclaredAnnotation(jakarta.persistence.GeneratedValue.class);

        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
    }

    public boolean isNullable() {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);
        return column == null || column.nullable();
    }
}
