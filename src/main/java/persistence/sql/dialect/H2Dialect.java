package persistence.sql.dialect;

import domain.H2DataType;
import domain.H2GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.Objects;

import static domain.Constraints.NOT_NULL;
import static domain.Constraints.NULL;

public class H2Dialect implements Dialect {

    @Override
    public String getTableName(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class) ? clazz.getAnnotation(Table.class).name()
                : clazz.getSimpleName().toLowerCase();
    }

    @Override
    public String getFieldName(Field field) {
        if (isColumnField(field)) {
            return isBlankOrEmpty(field.getAnnotation(Column.class).name()) ? field.getName()
                    : field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    @Override
    public String getFieldType(Field field) {
        return H2DataType.from(field.getType()).name();
    }

    @Override
    public String getFieldLength(Field field) {
        return Objects.nonNull(getColumnLength(field)) ? "(" + getColumnLength(field) + ")" : null;
    }

    @Override
    public String getGenerationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return H2GenerationType.from(field.getAnnotation(GeneratedValue.class).strategy()).getStrategy();
        }
        return null;
    }

    @Override
    public String getColumnNullConstraint(Field field) {
        if (!isColumnField(field) || field.getAnnotation(Column.class).nullable()) {
            return NULL.getName();
        }
        return NOT_NULL.getName();
    }

    private boolean isColumnField(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private boolean isBlankOrEmpty(String target) {
        return target.isBlank() || target.isEmpty();
    }

    private String getColumnLength(Field field) {
        if (isColumnField(field) && isVarcharType(field)) {
            return String.valueOf(field.getAnnotation(Column.class).length());
        }

        if (isColumnField(field) && !isVarcharType(field)) {
            return getLengthOrDefaultValue(field, 255);
        }

        return null;
    }

    private boolean isVarcharType(Field field) {
        return H2DataType.from(field.getType()).equals(H2DataType.VARCHAR);
    }

    private String getLengthOrDefaultValue(Field field, int defaultLengthValue) {
        return field.getAnnotation(Column.class).length() == defaultLengthValue ? null
                : String.valueOf(field.getAnnotation(Column.class).length());
    }

}
