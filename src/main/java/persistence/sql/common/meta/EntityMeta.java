package persistence.sql.common.meta;

import jakarta.persistence.Entity;
import persistence.exception.InvalidEntityException;
import utils.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class EntityMeta {
    private final TableName name;
    private final Column[] columns;

    protected <T> EntityMeta(T t) {
        isEntity(t.getClass());

        this.name = TableName.of(t.getClass());
        this.columns = Column.of(t.getClass().getDeclaredFields());
    }

    protected <T> EntityMeta(Class<T> tClass) {
        isEntity(tClass);

        this.name = TableName.of(tClass);
        this.columns = Column.of(tClass.getDeclaredFields());
    }

    public static <T> EntityMeta of(Class<T> tClass) {
        return new EntityMeta(tClass);
    }

    /**
     * 해당 클래스에 @Entity가 존재하는지 확인
     */
    protected static <T> boolean isEntity(Class<T> tClass) {
        if (!isAnnotation(tClass, Entity.class)) {
            throw new InvalidEntityException();
        }
        return true;
    }

    /**
     * 해당 annotation이 있는지 확인
     */
    private static <T, A> boolean isAnnotation(Class<T> tClass, Class<A> aClass) {
        Class<? extends Annotation> annotation = aClass.asSubclass(Annotation.class);

        return tClass.isAnnotationPresent(annotation);
    }

    public String getTableName() {
        return name.getValue();
    }

    /**
     * 칼럼명을 ','으로 이어 한 문자열로 반환합니다.
     * 예) "name, age, gender"
     */
    public String getColumnsWithComma() {
        return StringUtils.withComma(Arrays.stream(columns)
                .map(Column::getName)
                .toArray(String[]::new));
    }

    protected String getPrimaryKeyWithComma() {
        return StringUtils.withComma(Arrays.stream(columns).filter(Column::isPrimaryKey)
                .map(Column::getName)
                .toArray(String[]::new));
    }

    protected String getConstraintsWithColumns() {
        return StringUtils.withComma(Arrays.stream(columns)
                .map(column -> column.getName()
                    + column.getType()
                    + column.getConstraints().getNotNull()
                    + column.getConstraints().getGeneratedValue())
                .toArray(String[]::new));
    }

    public String getIdName() {
        return Arrays.stream(columns)
            .filter(Column::isPrimaryKey)
            .findFirst()
            .get()
            .getName();
    }

    public EntityMeta getTable() {
        return this;
    }
}
