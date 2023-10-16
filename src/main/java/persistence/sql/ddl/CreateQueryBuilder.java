package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class CreateQueryBuilder implements QueryBuilder {


    private static final String SPACE = " ";
    private static final String CREATE_TABLE = "CREATE TABLE";

    @Override
    public String getQuery(Object entity) {
        StringBuilder stringBuilder = new StringBuilder(CREATE_TABLE);
        stringBuilder.append(SPACE);
        stringBuilder.append(getTableName(entity));
        stringBuilder.append(SPACE);
        return stringBuilder.append("(ID BIGINT NOT NULL, AGE INTEGER, NAME VARCHAR(255), PRIMARY KEY (ID))").toString();
    }

    @Override
    public String getTableName(Object entity) {
        Class<?> entityClass = entity.getClass();
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해 테이블을 생성할 수 있습니다.");
        }

        if (entityClass.isAnnotationPresent(Table.class)) {
            return getTableNameFromTableAnnotation(entityClass);
        }

        return getTableNameFromClassName(entityClass);
    }

    private static String getTableNameFromClassName(Class<?> entityClass) {
        return entityClass.getSimpleName().toUpperCase();
    }

    private static String getTableNameFromTableAnnotation(Class<?> entityClass) {
        String tableName = entityClass.getAnnotation(Table.class).name();
        if (tableName == null || tableName.isBlank()) {
            return getTableNameFromClassName(entityClass);
        }
        return tableName.toUpperCase();
    }

}
