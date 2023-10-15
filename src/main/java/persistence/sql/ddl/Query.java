package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

abstract class Query {
    private String tableName;
    private Field[] fields;

    protected  <T> Query(Class<T> tClass) {
        parseTableName(tClass);
        this.fields = tClass.getDeclaredFields();
    }

    /**
     * class의 이름을 가져와 table 이름으로 설정합니다.
     */
    <T> void parseTableName(Class<T> tClass) {
        Class<Table> annotation = Table.class;
        String tableName = tClass.getSimpleName();

        if (isAnnotation(tClass, annotation)
                && !"".equals(tClass.getAnnotation(annotation).name())) {
            tableName = tClass.getAnnotation(annotation).name();
        }

        this.tableName = tableName;
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

    static <A> boolean isAnnotation(Field field, Class<A> aClass) {
        Class<? extends Annotation> annotation = aClass.asSubclass(Annotation.class);

        return field.isAnnotationPresent(annotation);
    }

    public String getTableName() {
        return tableName;
    }

    public Field[] getFields() {
        return fields;
    }
}
