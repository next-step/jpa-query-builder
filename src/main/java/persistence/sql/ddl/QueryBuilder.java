package persistence.sql.ddl;

import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.common.SqlConstant.*;

public class QueryBuilder {
    private final Field[] fields;
    private final String tableName;

    public QueryBuilder(Class<?> entity) {
        EntityValidator.validate(entity);
        this.fields = entity.getDeclaredFields();
        this.tableName = new Table(entity).getName();
    }

    // TODO: ANNOTAITON이 있는 경우와 없는 경우를 하나의 함수로 관리해보자.
    public String getCreateQuery() {
        return String.format(CREATE_TABLE_START, tableName) +
                getColumnQuery() +
                CREATE_TABLE_END;
    }

    private String getColumnQuery() {
        return Arrays.stream(fields).map(field -> {
                    return new AnnotationFreeColumn(field).getColumn();
                })
                .reduce((s1, s2) -> s1 + CRETE_TABLE_COMMA + s2)
                .orElse("");
    }

    public String getCreateQueryUsingAnnotation() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(CREATE_TABLE_START, tableName));

        Arrays.stream(fields).filter(AnnotatedColumn::isColumn).forEach(field -> {
            queryBuilder.append(new AnnotatedColumn(field).getColumn());
            queryBuilder.append(CRETE_TABLE_COMMA);
        });

        endQuery(queryBuilder);

        return queryBuilder.toString();
    }

    private void endQuery(StringBuilder queryBuilder) {
        int lastCommaIdx = queryBuilder.lastIndexOf(CRETE_TABLE_COMMA);
        queryBuilder.deleteCharAt(lastCommaIdx);
        queryBuilder.append(CREATE_TABLE_END);
    }

    public String getDropTableQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format(DROP_TABLE, tableName));
        return queryBuilder.toString();
    }
}
