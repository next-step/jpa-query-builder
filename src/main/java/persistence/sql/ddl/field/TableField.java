package persistence.sql.ddl.field;

import jakarta.persistence.Table;
import org.h2.util.StringUtils;

public class TableField {

    private final Class<?> klass;

    public TableField(Class<?> klass) {
        this.klass = klass;
    }

    public String toSQL() {
        Table annotation = klass.getAnnotation(Table.class);
        if (annotation != null && !StringUtils.isNullOrEmpty(annotation.name())) {
            return annotation.name();
        }
        return klass.getSimpleName();
    }
}
