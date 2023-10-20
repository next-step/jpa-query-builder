package persistence.sql.dialect.h2;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

import static persistence.sql.dialect.h2.H2Dialect.*;

/**
 * 컬럼 프로퍼티 쿼리 생성을 위한 클래스
 */
public class H2ColumnTypeProperties {

    // Varchar인 경우, length 표시
    public static String getVarcharLength(Field entityField) {
        Column columnAnnotation = entityField.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null) {
            return OPEN_PARENTHESIS + VARCHAR_DEFAULT_LENGTH + CLOSE_PARENTHESIS;
        }
        return OPEN_PARENTHESIS + entityField.getDeclaredAnnotation(Column.class).length() + CLOSE_PARENTHESIS;
    }

}
