package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

abstract class QueryBuilder {

    abstract String buildQuery(Class<?> clazz);

    protected void checkIsEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }
    }

    protected String findTableName(Class<?> clazz) {
        if(clazz.isAnnotationPresent(Table.class)) {
            return clazz.getDeclaredAnnotation(Table.class).name();
        }

        return clazz.getSimpleName();
    }
}
