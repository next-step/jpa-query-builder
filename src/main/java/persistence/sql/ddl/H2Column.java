package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.ddl.h2.Nullable;
import persistence.sql.ddl.h2.PKGenerationType;

import java.lang.reflect.Field;

public class H2Column {
    public static final String BLANK_STRING = "";
    private final Field field;

    public H2Column(final Field field) {
        this.field = field;
    }

    public String generateColumnSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(getColumnName());
        sb.append(" ");
        sb.append(getDataType());
        sb.append(" ");
        sb.append(getColumnNullable());
        if (isPK()) {
            sb.append(" ");
            sb.append(getGenerationType());
        }
        return sb.toString().trim();
    }

    public String getColumnName() {
        if (hasColumnAnnotation()) {
            return getColumnNameByAnnotation();
        }
        return getColumnNameByField();
    }

    private String getColumnNameByAnnotation() {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation.name().isEmpty()) {
            return getColumnNameByField();
        }
        return annotation.name();
    }

    private String getColumnNameByField() {
        return field.getName();
    }

    public String getColumnNullable() {
        return Nullable.getSQL(field);
    }

    public String getDataType() {
        return DataType.getDBType(field.getType().getSimpleName());
    }

    public String getGenerationType() {
        return PKGenerationType.getSQL(field);
    }


    private boolean hasColumnAnnotation() {
        return field.isAnnotationPresent(Column.class);
    }

    private boolean isPK() {
        return field.isAnnotationPresent(Id.class);
    }
}
