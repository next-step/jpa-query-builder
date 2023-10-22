package persistence.sql.common.meta;

import jakarta.persistence.Table;

import java.lang.annotation.Annotation;

class TableName {
    private final String value;

    private <T> TableName(Class<T> tClass) {
        this.value = parseTableName(tClass);
    }

    protected static <T> TableName of(Class<T> tClass) {
        return new TableName(tClass);
    }

    /**
     * class의 이름을 가져와 table 이름으로 설정합니다.
     */
    <T> String parseTableName(Class<T> tClass) {
        Class<Table> annotation = Table.class;
        String tableName = tClass.getSimpleName();

        if (isTable(tClass, annotation)
                && !"".equals(tClass.getAnnotation(annotation).name())) {
            tableName = tClass.getAnnotation(annotation).name();
        }

        return tableName;
    }

    private static <T, A> boolean isTable(Class<T> tClass, Class<A> aClass) {
        Class<? extends Annotation> annotation = aClass.asSubclass(Annotation.class);

        return tClass.isAnnotationPresent(annotation);
    }

    public String getValue() {
        return value;
    }
}
