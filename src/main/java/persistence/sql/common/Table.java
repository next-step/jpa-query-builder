package persistence.sql.common;

import jakarta.persistence.Entity;
import persistence.exception.InvalidEntityException;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public abstract class Table {

    private final TableName name;
    private final Column[] columns;
    private final Value[] values;

    protected <T> Table(T t) {
        this.name = TableName.of(t.getClass());
        this.columns = Column.of(t.getClass().getDeclaredFields());
        this.values = Value.of(t);
    }

    protected <T> Table(Class<T> tClass) {
        this.name = TableName.of(tClass);
        this.columns = Column.of(tClass.getDeclaredFields());
        this.values = null;
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

    protected String getTableName() {
        return name.getValue();
    }

    /**
     * 칼럼명을 ','으로 이어 한 문자열로 반환합니다.
     * 예) "name, age, gender"
     */
    protected String getColumnsWithComma() {
        return withComma(Arrays.stream(columns)
                .map(Column::getName)
                .toArray(String[]::new));
    }

    /**
     * 값을 ','으로 이어 한 문자열로 반환합니다.
     * 예) "홍길동, 13, F"
     */
    protected String getValuesWithComma() {
        return withComma(Arrays.stream(values)
                .map(Value::getValue)
                .toArray(String[]::new));
    }

    protected String getPrimaryKeyWithComma() {
        return withComma(Arrays.stream(columns).filter(Column::isPrimaryKey)
                .map(Column::getName)
                .toArray(String[]::new));
    }

    protected String getConstraintsWithColumns() {
        return withComma(Arrays.stream(columns)
                .map(column -> column.getName()
                    + column.getType()
                    + column.getConstraints().getNotNull()
                    + column.getConstraints().getGeneratedValue())
                .toArray(String[]::new));
    }

    private String withComma(String[] input) {
        return String.join(", ", input);
    }
}
