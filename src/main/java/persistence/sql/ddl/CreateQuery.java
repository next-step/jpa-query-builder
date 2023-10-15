package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

class CreateQuery extends Query {

    public static final String DEFAULT_CREATE_QUERY = "CREATE TABLE %s (%s)";
    public static final String DEFAULT_PRIMARY_KEY_QUERY = ", PRIMARY KEY (%s)";

    private final Field[] fields;
    private String columns;

    private <T> CreateQuery(Class<T> tClass) {
        super(tClass);
        this.fields = tClass.getDeclaredFields();
    }

    public static <T> CreateQuery initCreateQuery(Class<T> tClass) {
        if (!isEntity(tClass)) {
            throw new NullPointerException();
        }

        CreateQuery createQuery = new CreateQuery(tClass);
        createQuery.createColumns();

        return createQuery;
    }

    /**
     * 해당 Class를 분석하여 CREATE QUERY로 조합합니다.
     */
    public String crateQuery() {
        return String.format(DEFAULT_CREATE_QUERY, this.getTableName(), columns);
    }

    private void createColumns() {
        this.columns = parseColumn() + parsePrimary();
    }

    /**
     * class 필드를 읽어 column으로 설정합니다.
     */
    private String parseColumn() {
        String[] fieldArray = Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(CreateColumn::makeColumn)
                .toArray(String[]::new);

        return String.join(", ", fieldArray);
    }

    /**
     * @Id로 설정된 필드를 Primary Key로 설정합니다.
     */
    private String parsePrimary() {
        String[] fieldArray = Arrays.stream(fields).filter(field -> isAnnotation(field, Id.class))
                .map(Field::getName)
                .toArray(String[]::new);

        return String.format(DEFAULT_PRIMARY_KEY_QUERY, String.join(", ", fieldArray));
    }

    private <A> boolean isAnnotation(Field field, Class<A> aClass) {
        Class<? extends Annotation> annotation = aClass.asSubclass(Annotation.class);

        return field.isAnnotationPresent(annotation);
    }
}
