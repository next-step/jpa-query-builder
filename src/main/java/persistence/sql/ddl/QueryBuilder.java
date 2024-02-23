package persistence.sql.ddl;

import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.common.SqlConstant.*;

public class QueryBuilder {
    private final StringBuilder queryBuilder;
    private final Field[] fields;

    public QueryBuilder(Class<?> entity) {
        EntityValidator.validate(entity);
        this.fields = entity.getDeclaredFields();
        this.queryBuilder = new StringBuilder(String.format(CREATE_TABLE_START, new Table(entity).getName()));
    }

    public String getCreateQuery() {
        Arrays.stream(fields).forEach(field -> {
            queryBuilder.append(new AnnotationFreeColumn(field).getColumn());
            queryBuilder.append(CRETE_TABLE_COMMA);
        });
        endQuery();

        return queryBuilder.toString();
    }

    public String buildWithAnnotation() {
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
}
