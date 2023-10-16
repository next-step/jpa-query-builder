package persistence.sql.common;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

class ColumnName {
    private final String value;

    private ColumnName(Field field) {
        this.value = extractName(field);
    }

    protected static ColumnName of(Field field) {
        return new ColumnName(field);
    }

    /**
     * @Column의 name이 유효하다면 column명으로 설정하여 반환합니다.
     * 없을 경우 기존 field명을 반환합니다.
     */
    private String extractName(Field field) {
        String columnName = field.getName();

        if (!field.isAnnotationPresent(Column.class)) {
            return columnName;
        }

        if (!"".equals(field.getDeclaredAnnotation(Column.class).name())) {
            columnName = field.getDeclaredAnnotation(Column.class).name();
        }

        return columnName;
    }

    public String value() {
        return value;
    }
}
