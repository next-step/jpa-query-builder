package persistence.sql.entity;

import jakarta.persistence.Table;

/**
 * 엔티티 이름 추출
 */
public class EntityName {

    private final Class<?> entityClass;

    public EntityName(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 테이블 이름을 추출
     */
    public String getTableName() {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return getTableNameFromTableAnnotation();
        }
        return getTableNameFromClassName();
    }

    /**
     * 클래스명에서 테이블 이름 추출
     */
    private String getTableNameFromClassName() {
        return entityClass.getSimpleName().toLowerCase();
    }

    /**
     * `@Table`에서 이름 추출
     */
    private String getTableNameFromTableAnnotation() {
        String tableName = entityClass.getAnnotation(Table.class).name();
        if (tableName == null || tableName.isBlank()) {
            return getTableNameFromClassName();
        }
        return tableName;
    }

}
