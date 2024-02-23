package persistence.sql.ddl;

import jakarta.persistence.Column;
import persistence.sql.exception.InvalidFieldException;
import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static persistence.sql.common.SqlConstant.*;
import static persistence.sql.ddl.utils.IdChecker.isId;

public class QueryBuilder {

    private static final Map<Type, Function<String, String>> annotationFreeMap = Map.of(
            String.class, fieldName -> String.format(CREATE_TABLE_VARCHAR_COLUMN_NULLABLE, fieldName),
            Integer.class, fieldName -> String.format(CREATE_TABLE_COLUMN_INT, fieldName)
    );
    
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
            queryBuilder.append(getAnnotationFreeColumn(fields[x]));
            addConnector(x, fieldsCount, queryBuilder);
        });

        return queryBuilder.toString();
    }

    /**
     * field 값을 바탕으로 colum에 대한 sql 정의를 리턴합니다.<br>
     * 애노테이션이 적용되지 않은 Entity를 컨버팅시 본 메서드를 사용합니다.
     */
    public String getAnnotationFreeColumn(Field field) {
        if (isId(field)) {
            return CREATE_TABLE_ID_COLUMN;
        }
        return annotationFreeMap.get(field.getType()).apply(field.getName());
    }
    
    public String buildWithAnnotation() {
        StringBuilder queryBuilder = new StringBuilder(String.format(CREATE_TABLE_START, entity.getSimpleName()));

        Field[] fields = entity.getDeclaredFields();

        List<String> columns = getColumns(fields);

        for (int i = 0; i < columns.size(); i++) {
            queryBuilder.append(columns.get(i));
            addConnector(i, columns.size(), queryBuilder);
        }
        return queryBuilder.toString();
    }

    private List<String> getColumns(Field[] fields) {
        return Arrays.stream(fields).map(field -> {
            if (isId(field)) {
                return CREATE_TABLE_ID_COLUMN;
            }
            String name = getName(field);
            boolean nullable = isNullable(field);
            Class<?> type = field.getType();
            if (type.equals(String.class)) {
                return getVarcharColumn(nullable, name);
            }
            if (type.equals(Integer.class)) {
                return String.format(CREATE_TABLE_COLUMN_INT, name);
            }
            throw new InvalidFieldException();
        }).toList();
    }

    private static String getVarcharColumn(boolean nullable, String name) {
        if(!nullable) {
            return String.format(CREATE_TABLE_VARCHAR_COLUMN_NOT_NULL, name);
        } else {
            return String.format(CREATE_TABLE_VARCHAR_COLUMN_NULLABLE, name);
        }
    }

    private String getName(Field field) {
        return field.getAnnotation(Column.class).name().isEmpty() ? field.getName() : field.getAnnotation(Column.class).name();
    }

    private boolean isNullable(Field field) {
        return field.getAnnotation(Column.class).nullable();
    }

    private void addConnector(int currentIdx, int fieldsCount, StringBuilder queryBuilder) {
        if (isLastIndex(currentIdx, fieldsCount)) {
            queryBuilder.append(CREATE_TABLE_END);
            return;
        }
        queryBuilder.append(CRETE_TABLE_COMMA);
    }

    private boolean isLastIndex(int currentIdx, int fieldsCount) {
        return currentIdx == fieldsCount - 1;
    }

}
