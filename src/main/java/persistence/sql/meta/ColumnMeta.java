package persistence.sql.meta;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import persistence.sql.util.StringConstant;
import persistence.sql.util.StringUtils;

import java.lang.reflect.Field;

public class ColumnMeta {

    public static boolean isTransient(Field field) {
        return field.getDeclaredAnnotation(Transient.class) != null;
    }

    public static String getColumnName(Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null || StringUtils.isNullOrEmpty(columnAnnotation.name())) {
            return field.getName().toLowerCase();
        }
        return columnAnnotation.name();
    }

    public static String getColumnValue(Object object, Field field) {
        field.setAccessible(true);
        Object fieldValue = null;
        try {
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("데이터 처리가 불가능한 속성입니다.");
        }
        if (fieldValue == null) {
            return StringConstant.NULL;
        }
        if (field.getType() == String.class) {
            return StringConstant.SINGLE_QUOTATION + fieldValue + StringConstant.SINGLE_QUOTATION;
        }
        return fieldValue.toString();
    }
}
