package persistence.sql.ddl;

import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static persistence.sql.common.SqlConstant.*;
import static persistence.sql.ddl.utils.LastColumnChecker.isLastColumn;

public class QueryBuilder {
    private final StringBuilder queryBuilder;
    private final Field[] fields;

    public QueryBuilder(Class<?> entity) {
        EntityValidator.validate(entity);
        this.fields = entity.getDeclaredFields();
        this.queryBuilder = new StringBuilder(String.format(CREATE_TABLE_START, new Table(entity).getName()));
    }

    public String buildWithoutAnnotation() {
        int fieldsCount = fields.length;

        IntStream.range(0, fieldsCount).forEach(x -> {
            queryBuilder.append(new AnnotationFreeColumn(fields[x]).getColumn());
            connectColumns(x);
        });

        return queryBuilder.toString();
    }


    public String buildWithAnnotation() {
        List<String> columns = getColumns();
        int size = columns.size();

        IntStream.range(0, size).forEach(idx -> {
            queryBuilder.append(columns.get(idx));
            connectColumns(idx);
        });

        return queryBuilder.toString();
    }

    private List<String> getColumns() {
        return Arrays.stream(fields)
                .map(field -> new AnnotatedColumn(field).getColumn())
                .toList();
    }

    private void connectColumns(int currentIdx) {
        if (isLastColumn(currentIdx, fields.length)) {
            queryBuilder.append(CREATE_TABLE_END);
            return;
        }
        queryBuilder.append(CRETE_TABLE_COMMA);
    }
}
