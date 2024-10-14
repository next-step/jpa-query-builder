package persistence.sql.ddl.mapping;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class TableColumn {

    private static final Long DEFAULT_LENGTH = 255L;

    private Class<?> javaType;
    private String name;
    private long length;
    private boolean nullable;

    public TableColumn(Field field) {
        this.javaType = field.getType();
        this.name = field.getName();
        this.length = DEFAULT_LENGTH;
        this.nullable = true;

        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            if (!annotation.name().isBlank()) {
                this.name = annotation.name();
            }
            this.nullable = annotation.nullable();
            this.length = annotation.length();
        }
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public String name() {
        return name;
    }

    public long length() {
        return length;
    }

    public boolean nullable() {
        return nullable;
    }

}
