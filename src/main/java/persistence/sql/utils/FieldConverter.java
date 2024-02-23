package persistence.sql.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

import static persistence.sql.common.SqlConstant.*;

public class FieldConverter {
    private static final Map<Type, Function<String, String>> map = Map.of(
            String.class, fieldName -> String.format(CREATE_TABLE_VARCHAR_COLUMN_NULLABLE, fieldName),
            Integer.class, fieldName -> String.format(CREATE_TABLE_COLUMN_INT, fieldName)
    );

    /**
     * field 값을 바탕으로 colum에 대한 sql 정의를 리턴합니다
     */
    public static String getColumn(Field field) {
        if (isId(field)) {
            return CREATE_TABLE_ID_COLUMN;
        }
        return map.get(field.getType()).apply(field.getName());
    }

    private static boolean isId(Field field) {
        return field.getType().equals(Long.class) && field.getName().equals(COLUMN_ID);
    }
}
