package persistence.sql.ddl;

import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.common.SqlConstant.*;

public class QueryBuilder {
    private final StringBuilder queryBuilder;
    private final Field[] fields;
    private final String tableName;

    public QueryBuilder(Class<?> entity) {
        EntityValidator.validate(entity);
        this.fields = entity.getDeclaredFields();
        this.tableName = new Table(entity).getName();
        this.queryBuilder = new StringBuilder(String.format(CREATE_TABLE_START, tableName));
    }

    public String getCreateQuery() {
        initQueryBuilder();
        Arrays.stream(fields).forEach(field -> {
            queryBuilder.append(new AnnotationFreeColumn(field).getColumn());
            queryBuilder.append(CRETE_TABLE_COMMA);
        });
        endQuery();

        return queryBuilder.toString();
    }

    private void initQueryBuilder() {
        queryBuilder.setLength(0);
    }

    public String getCreateQueryUsingAnnotation() {
        Arrays.stream(fields).filter(AnnotatedColumn::isColumn).forEach(field -> {
            queryBuilder.append(new AnnotatedColumn(field).getColumn());
            queryBuilder.append(CRETE_TABLE_COMMA);
        });

        endQuery();

        return queryBuilder.toString();
    }

    private void endQuery() {
        int lastCommaIdx = queryBuilder.lastIndexOf(CRETE_TABLE_COMMA);
        queryBuilder.deleteCharAt(lastCommaIdx);
        queryBuilder.append(CREATE_TABLE_END);
    }

    public String dropTable() {
        initQueryBuilder();
        queryBuilder.append(String.format(DROP_TABLE, tableName));
        return queryBuilder.toString();
    }
}
