package persistence.sql.ddl;

import jakarta.persistence.Column;
import persistence.sql.exception.InvalidFieldException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static persistence.sql.common.SqlConstant.*;
import static persistence.sql.ddl.utils.IdChecker.isId;

/**
 * 애노테이션이 있는 필드는 본 클래스로 column sql문 생성한다.
 */
public class AnnotatedColumn {
    private final Field field;
    private final String name;
    private final Class<?> type;
    private final boolean nullable;

    public AnnotatedColumn(Field field) {
        this.field = field;
        this.name = getName();
        this.type = field.getType();
        this.nullable = isNullable();
    }

    public String getColumn() {
        if (isId(field)) {
            return CREATE_TABLE_ID_COLUMN;
        }
        if (String.class.equals(type)) {
            return getVarcharColumn(nullable, name);
        }
        if (Integer.class.equals(type)) {
            return String.format(CREATE_TABLE_COLUMN_INT, name);
        }
        throw new InvalidFieldException();
    }

    private static String getVarcharColumn(boolean nullable, String name) {
        if (nullable) {
            return String.format(CREATE_TABLE_VARCHAR_COLUMN_NULLABLE, name);
        }
        return String.format(CREATE_TABLE_VARCHAR_COLUMN_NOT_NULL, name);
    }

    private String getName() {
        if (isId(field)) {
            return "";
        }
        if (field.getAnnotation(Column.class).name().isEmpty()) {
            return field.getName();
        }
        return field.getAnnotation(Column.class).name();
    }

    private boolean isNullable() {
        if (isId(field)) {
            return false;
        }
        return field.getAnnotation(Column.class).nullable();
    }

}
