package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.lang.annotation.Annotation;

abstract class Query {
    private final String tableName;


    protected <T> Query(Class<T> tClass) {
        this.tableName = parseTableName(tClass);
    }

    /**
     * class의 이름을 가져와 table 이름으로 설정합니다.
     */
    <T> String parseTableName(Class<T> tClass) {
        Class<Table> annotation = Table.class;
        String tableName = tClass.getSimpleName();

        if (isAnnotation(tClass, annotation)
                && !"".equals(tClass.getAnnotation(annotation).name())) {
            tableName = tClass.getAnnotation(annotation).name();
        }

        return tableName;
    }

    /**
     * 해당 클래스에 @Entity가 존재하는지 확인
     */
    static <T> boolean isEntity(Class<T> tClass) {
        return isAnnotation(tClass, Entity.class);
    }

    /**
     * 해당 annotation이 있는지 확인
     */
    static <T, A> boolean isAnnotation(Class<T> tClass, Class<A> aClass) {
        Class<? extends Annotation> annotation = aClass.asSubclass(Annotation.class);

        return tClass.isAnnotationPresent(annotation);
    }

    public String getTableName() {
        return tableName;
    }
}
