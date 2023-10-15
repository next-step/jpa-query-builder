package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class QueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);

    public static <T> String createTableSQL(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        final StringBuilder queryBuilder = new StringBuilder();
        final String tableName = clazz.getSimpleName().toLowerCase();

        queryBuilder.append("create table ").append(tableName);
        final Field [] fields = clazz.getDeclaredFields();

        queryBuilder.append(" (");
        queryBuilder.append(String.join(", ", getCreateColumnQueries(tableName, fields)));
        queryBuilder.append(");");

        return queryBuilder.toString();
    }

    static ArrayList<String> getCreateColumnQueries(String tableName, Field [] fields) {
        final ArrayList<String> columnQueries = new ArrayList<>();

        final Field primaryKeyField = findPrimaryFieldOrElseThrow(fields);
        final String primaryKeyQuery = primaryKeyField.getName() + " bigint not null constraint " + tableName + "_pkey primary key";
        columnQueries.add(primaryKeyQuery);

        findNonPrimaryFields(fields)
                .filter(x -> changeJavaClassToJdbcTypeCode(x.getType()) != null)
                .forEach(x -> columnQueries.add(x.getName() + " " + changeJavaClassToJdbcTypeCode(x.getType())));

        return columnQueries;
    }

    static <T> String changeJavaClassToJdbcTypeCode(Class<T> clazz) {
        switch (clazz.getSimpleName()) {
        case "String":
            return "varchar(255)";
        case "Integer":
            return "integer";
        default:
            logger.warn("Incompatible database type: " + clazz.getSimpleName());
            return null;
        }
    }

    static Field findPrimaryFieldOrElseThrow(Field[] fields) {
        return Arrays
                .stream(fields)
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    static Stream<Field> findNonPrimaryFields(Field [] fields) {
        return  Arrays
                .stream(fields)
                .filter(x -> !x.isAnnotationPresent(Id.class));
    }
}
