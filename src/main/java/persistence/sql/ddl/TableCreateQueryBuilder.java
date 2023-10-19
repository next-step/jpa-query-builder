package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableCreateQueryBuilder extends QueryBuilder {

    public TableCreateQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public String generateSQLQuery(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        final StringBuilder queryBuilder = new StringBuilder();
        String tableName = TableQueryUtil.getTableName(clazz);
        queryBuilder.append("CREATE TABLE ").append(tableName);
        queryBuilder.append(" (");

        final Field[] fields = clazz.getDeclaredFields();

        final TableFieldGenerator tableFieldGenerator = new TableFieldGenerator(this.dialect, this.mappings);
        queryBuilder.append(
            Arrays
                .stream(fields)
                .map(tableFieldGenerator::generate)
                .filter(Objects::nonNull)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining(", "))
        );

        final FieldConstraintsGenerator fieldConstraintsGenerator = new FieldConstraintsGenerator();
        List<String> constraints = Arrays
            .stream(fields)
            .map(fieldConstraintsGenerator::generate)
            .filter(Objects::nonNull)
            .filter(x -> !x.isEmpty())
            .collect(Collectors.toList());
        if (!constraints.isEmpty()) {
            queryBuilder.append(", ");
            queryBuilder.append(String.join(", ", constraints));
        }

        queryBuilder.append(");");

        return queryBuilder.toString();
    }
}
