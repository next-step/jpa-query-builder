package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import persistence.sql.exception.InvalidEntityException;
import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static persistence.sql.common.SqlConstant.*;
import static persistence.sql.utils.FieldConverter.getColumn;

public class QueryBuilder {
    private final Class<?> entity;

    public QueryBuilder(Class<?> entity) {
        EntityValidator.validate(entity);
        this.entity = entity;
    }

    public String buildWithoutAnnotation() {
        StringBuilder queryBuilder = new StringBuilder(String.format(CREATE_TABLE_START, entity.getSimpleName()));

        Field[] fields = entity.getDeclaredFields();
        int fieldsCount = fields.length;

        IntStream.range(0, fieldsCount).forEach(x-> {
            queryBuilder.append(getColumn(fields[x]));
            addConnector(x, fieldsCount, queryBuilder);
        });

        return queryBuilder.toString();
    }

    public String buildWithAnnotation() {
        StringBuilder queryBuilder = new StringBuilder(String.format(CREATE_TABLE_START, entity.getSimpleName()));

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
            queryBuilder.append(CREATE_TABLE_END);
            return;
        }
        queryBuilder.append(CRETE_TABLE_COMMA);
    }

    private static boolean isLastIndex(int currentIdx, int fieldsCount) {
        return currentIdx == fieldsCount - 1;
    }

}
