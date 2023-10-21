package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableFieldUtil;
import persistence.sql.TableQueryUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableCreateQueryBuilder extends QueryBuilder {

    public TableCreateQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) {
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        final StringBuilder queryBuilder = new StringBuilder();
        String tableName = TableQueryUtil.getTableName(clazz);
        queryBuilder.append("CREATE TABLE ").append(tableName);
        queryBuilder.append(" (");

        final Field[] fields = TableFieldUtil.getAvailableFields(clazz);

        final TableFieldGenerator tableFieldGenerator = new TableFieldGenerator(this.dialect);
        queryBuilder.append(
            Arrays
                .stream(fields)
                .map(tableFieldGenerator::generate)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining(", "))
        );

        final FieldConstraintsGenerator fieldConstraintsGenerator = new FieldConstraintsGenerator();
        List<String> constraints = Arrays
            .stream(fields)
            .map(fieldConstraintsGenerator::generate)
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
