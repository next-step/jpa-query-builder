package persistence.sql.ddl.mapping;

import jakarta.persistence.Column;
import persistence.sql.ddl.type.ColumnType;

import java.lang.reflect.Field;

public class TableColumn {

    private static final long DEFAULT_LENGTH = 255L;

    private final int type;
    private String name;
    private long length;
    private boolean nullable;

    public TableColumn(Field field) {
        this.type = ColumnType.getSqlType(field.getType());
        setName(field);
        setLength(field);
        setNullable(field);
    }

    private void setName(Field field) {
        if (existColumnNameProperty(field)) {
            Column annotation = field.getAnnotation(Column.class);
            this.name = annotation.name();
            return;
        }
        this.name = field.getName();
    }

    private void setLength(Field field) {
        if (ColumnType.isNotVarcharType(field.getType())) {
            this.length = 0;
            return;
        }

        if (notExistColumnAnnotation(field)) {
            this.length = DEFAULT_LENGTH;
            return;
        }

        Column annotation = field.getAnnotation(Column.class);
        this.length = annotation.length();
    }

    private void setNullable(Field field) {
        if (notExistColumnAnnotation(field)) {
            this.nullable = true;
            return;
        }
        Column annotation = field.getAnnotation(Column.class);
        this.nullable = annotation.nullable();
    }

    private boolean existColumnNameProperty(Field field) {
        if (notExistColumnAnnotation(field)) {
            return false;
        }
        Column annotation = field.getAnnotation(Column.class);
        return notBlank(annotation.name());
    }

    private boolean notExistColumnAnnotation(Field field) {
        return !field.isAnnotationPresent(Column.class);
    }

    private boolean notBlank(String name) {
        return !name.isBlank();
    }

    public boolean notNull() {
        return !nullable;
    }

    public int type() {
        return type;
    }

    public String name() {
        return name;
    }

    public long length() {
        return length;
    }

}
