package persistence.sql.dialect.h2;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

/**
 * 컬럼 프로퍼티 쿼리 생성을 위한 클래스
 */
public class H2ColumnTypeProperties {

    // Varchar인 경우, length 표시
    public static String getVarcharLength(Field entityField) {
        Column columnAnnotation = entityField.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null) {
            return H2Query.OPEN_PARENTHESIS + H2Query.VARCHAR_DEFAULT_LENGTH + H2Query.CLOSE_PARENTHESIS;
        }
        return H2Query.OPEN_PARENTHESIS + entityField.getDeclaredAnnotation(Column.class).length() + H2Query.CLOSE_PARENTHESIS;
    }

}
