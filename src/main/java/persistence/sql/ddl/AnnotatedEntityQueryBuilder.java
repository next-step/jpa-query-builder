package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class AnnotatedEntityQueryBuilder {
    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (";

    public static final String CLOSING_PARENTHESIS = ")";
    public static final String COMMA = ",";
    private final Class<?> entity;

    public AnnotatedEntityQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity가 아닌 클래스는 본 클래스의 생성자에 넣을 수 없습니다.");
        }
        this.entity = entity;
    }

    public String build() {
        StringBuilder queryBuilder = new StringBuilder(String.format(SQL_CREATE_TABLE, entity.getSimpleName()));

        Field[] fields = entity.getDeclaredFields();

        List<String> columns = Arrays.stream(fields).map(field -> {
            if (field.getType().equals(Long.class) && field.getName().equals("id")) {
                return "id INT AUTO_INCREMENT PRIMARY KEY";
            }
            String name = field.getAnnotation(Column.class).name().isEmpty() ? field.getName() :  field.getAnnotation(Column.class).name();
            boolean nullable = field.getAnnotation(Column.class).nullable();
            Class<?> type = field.getType();

            if (type.equals(String.class) && !nullable) {
                return String.format("%s VARCHAR(30) NOT NULL", name);
            }
            if (type.equals(String.class) && nullable) {
                return String.format("%s VARCHAR(30) NULL", name);
            }
            if (type.equals(Integer.class)) {
                return String.format("%s INT", name);
            }
            throw new IllegalArgumentException("유효하지 않은 필드입니다.");
        }).toList();

        for (int i = 0; i < columns.size(); i++) {
            queryBuilder.append(columns.get(i));
            addConnector(i, columns.size(), queryBuilder);
        }
        return queryBuilder.toString();
    }

    private static void addConnector(int currentIdx, int fieldsCount, StringBuilder queryBuilder) {
        if (isLastIndex(currentIdx, fieldsCount)) {
            queryBuilder.append(CLOSING_PARENTHESIS);
            return;
        }
        queryBuilder.append(COMMA);
    }

    private static boolean isLastIndex(int currentIdx, int fieldsCount) {
        return currentIdx == fieldsCount - 1;
    }

}
