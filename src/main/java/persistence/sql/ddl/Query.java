package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.exception.NotEntityException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Query {
    private static final String DELIMITER = " ";
    private final String sql;

    public Query(QueryType queryType, Class<?> targetClass) {
        checkIsEntity(targetClass);
        String ddlType = queryType.getValue();
        String tableName = targetClass.getSimpleName();
        String tableFields = ColumnQueries.of(targetClass).toQuery();
        this.sql = String.join(DELIMITER, List.of(ddlType, tableName, tableFields));
    }

    private void checkIsEntity(Class<?> targetClass) {
        boolean isEntity = Arrays.stream(targetClass.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .collect(Collectors.toList())
                .contains(Entity.class);
        if (!isEntity) {
            throw new NotEntityException();
        }
    }

    public String getSql() {
        return sql;
    }
}
