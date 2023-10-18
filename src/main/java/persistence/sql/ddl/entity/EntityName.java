package persistence.sql.ddl.entity;

import jakarta.persistence.Table;

/**
 * 엔티티 이름 추출
 */
public class EntityName {

    /**
     * 테이블 이름을 추출
     */
    public static String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return getTableNameFromTableAnnotation(entityClass);
        }
        return getTableNameFromClassName(entityClass);
    }

    /**
     * 클래스명에서 테이블 이름 추출
     */
    private static String getTableNameFromClassName(Class<?> entityClass) {
        return entityClass.getSimpleName().toLowerCase();
    }

    /**
     * `@Table`에서 이름 추출
     */
    private static String getTableNameFromTableAnnotation(Class<?> entityClass) {
        String tableName = entityClass.getAnnotation(Table.class).name();
        if (tableName == null || tableName.isBlank()) {
            return getTableNameFromClassName(entityClass);
        }
        return tableName;
    }

}
