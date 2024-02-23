package persistence.sql.ddl.utils;

import java.lang.reflect.Field;

import static persistence.sql.common.SqlConstant.COLUMN_ID;

public class IdChecker {
    public static boolean isId(Field field) {
        return field.getType().equals(Long.class) && field.getName().equals(COLUMN_ID);
    }
}
