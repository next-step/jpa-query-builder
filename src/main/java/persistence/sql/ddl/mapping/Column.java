package persistence.sql.ddl.mapping;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class Column {

    private Class<?> javaType;
    private String name;
    private long length;
    private boolean nullable;
    private boolean identity;

    public Column(Field field) {
        this.javaType = field.getType();
        this.name = field.getName();
        this.length = 255L;
        this.nullable = true;
        this.identity = false;

        if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            jakarta.persistence.Column annotation = field.getAnnotation(jakarta.persistence.Column.class);
            if (!annotation.name().isBlank()) {
                this.name = annotation.name();
            }
            this.nullable = annotation.nullable();
            this.length = annotation.length();
        }

        if (field.isAnnotationPresent(Id.class)) {
            this.identity = true;
        }
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isIdentity() {
        return identity;
    }
}
