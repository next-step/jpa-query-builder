package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import persistence.sql.meta.EntityMeta;
import persistence.sql.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CreateQueryBuilder {

    private static final String CREATE_HEADER = "CREATE TABLE ";

    public static String getQuery(Class<?> clazz) {

        // Domain 클래스 검증
        validateEntity(clazz);

        return buildQuery(clazz);
    }

    private static void validateEntity(Class<?> clazz) {
        validateEntityAnnotation(clazz);
        validatePkAnnotation(clazz);
    }

    private static void validateEntityAnnotation(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Create Query 빌드 대상이 아닙니다.");
        }
    }

    private static void validatePkAnnotation(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        boolean isPkPresent = Arrays.stream(declaredFields)
                .anyMatch(declaredField -> declaredField.getDeclaredAnnotation(Id.class) != null);
        if (!isPkPresent) {
            throw new IllegalArgumentException("Primary Key가 정의되지 않았습니다.");
        }
    }

    private static String buildQuery(Class<?> clazz) {
        return new StringBuilder()
                .append(CREATE_HEADER)
                .append(getTableName(clazz))
                .append(" (")
                .append(buildColumns(clazz.getDeclaredFields()))
                .append(");")
                .toString();
    }

    private static String getTableName(Class<?> clazz) {
        Table tableAnnotation = clazz.getDeclaredAnnotation(Table.class);
        if (tableAnnotation != null && !StringUtils.isNullOrEmpty(tableAnnotation.name())) {
            return tableAnnotation.name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    private static String buildColumns(Field[] fields) {
        return ColumnBuilder.getColumnDefinition(fields);
    }
}
