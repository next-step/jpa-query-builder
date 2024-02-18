package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.exception.NotEntityException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Query {
    private static final String DELIMITER = " ";
    private final String sql;

    public Query(QueryType queryType, Class<?> targetClass) {
        checkIsEntity(targetClass);
        String ddlType = queryType.getValue();
        String tableName = getTableName(targetClass);
        String tableFields = ColumnQueries.of(targetClass).toQuery();
        this.sql = String.join(DELIMITER, List.of(ddlType, tableName, tableFields));
    }

    private String getTableName(Class<?> targetClass) {
        return Optional.ofNullable(targetClass.getAnnotation(Table.class))
                .map(Table::name)
                .filter(name -> !name.isBlank())
                .orElse(targetClass.getSimpleName());
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
