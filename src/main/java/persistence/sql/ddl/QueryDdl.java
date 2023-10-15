package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class QueryDdl {
    public static final String DEFAULT_CREATE_QUERY = "CREATE TABLE %s (%s)";
    public static final String DEFAULT_PRIMARY_KEY_QUERY = ", PRIMARY KEY (%s)";

    public static <T> String create(Class<T> tClass) {
        return crateQuery(tClass);
    }

    /**
     * 해당 Class를 분석하여 CREATE QUERY로 조합합니다.
     */
    private static <T> String crateQuery(Class<T> tClass) {
        if(!tClass.isAnnotationPresent(Entity.class)) {
            return null;
        }

            String tableName = parseTableName(tClass);
        String columns = parseColumn(tClass) + parsePrimary(tClass);

        return String.format(DEFAULT_CREATE_QUERY, tableName, columns);
    }

    /**
     * class의 이름을 가져와 table 이름으로 설정합니다.
     */
    private static <T> String parseTableName(Class<T> tClass) {
        return tClass.getSimpleName();
    }

    /**
     * class 필드를 읽어 column으로 설정합니다.
     */
    private static <T> String parseColumn(Class<T> tClass) {
        Field[] fieldArray = tClass.getDeclaredFields();

        String[] fields = Arrays.stream(fieldArray)
                .map(field -> String.join(" ", field.getName(), parseType(field.getType())))
                .toArray(String[]::new);

        return String.join(", ", fields);
    }

    /**
     * @Id로 설정된 필드를 Primary Key로 설정합니다.
     */
    private static <T> String parsePrimary(Class<T> tClass) {
        Field[] filedArray = tClass.getDeclaredFields();

        String[] fields = Arrays.stream(filedArray).filter(method -> method.isAnnotationPresent(Id.class))
                .map(Field::getName)
                .toArray(String[]::new);

        return String.format(DEFAULT_PRIMARY_KEY_QUERY, String.join(", ", fields));
    }

    /**
     * 필드의 자료형을 읽어 칼럼 자료형으로 치환합니다.
     */
    private static <T> String parseType(Class<T> type) {
        switch (type.getSimpleName()) {
            case "int":
                return "INT";
            case "Integer":
                return "INTEGER";
            case "Long":
                return "BIGINT";
            case "String":
                return "VARCHAR(255)";
        }

        throw new IllegalArgumentException("유효하지 않은 자료형입니다.");
    }
}
