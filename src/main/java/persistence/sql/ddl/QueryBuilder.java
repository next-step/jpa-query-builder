package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class QueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
    private final Dialect dialect;
    private final ConcurrentHashMap<Class<?>, Integer> mappings;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.mappings = buildJavaClassToJdbcTypeCodeMappings();
    }

    public String createTableSQL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        final StringBuilder queryBuilder = new StringBuilder();
        final String tableName = clazz.getSimpleName().toLowerCase();

        queryBuilder.append("CREATE TABLE ").append(tableName);
        final Field [] fields = clazz.getDeclaredFields();

        queryBuilder.append(" (");
        queryBuilder.append(String.join(", ", getCreateColumnQueries(fields)));
        queryBuilder.append(");");

        return queryBuilder.toString();
    }

    List<String> getCreateColumnQueries(Field [] fields) {
        return Arrays
                .stream(fields)
                .filter(x -> mappings.containsKey(x.getType()))
                .map(this::fieldToColumnQuery)
                .collect(Collectors.toList());
    }

    public String fieldToColumnQuery(Field field) {
        final StringBuilder stringBuilder = new StringBuilder();

        String columName = field.getName();
        boolean isNotNullable = false;
        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            if (!annotation.name().isEmpty()) {
                columName = annotation.name();
            }
            if (!annotation.nullable()) {
                isNotNullable = true;
            }
        }

        stringBuilder.append(getColumnNameBySingleQuote(columName))
                .append(" ")
                .append(getTypeName(field.getType()));
        if (isNotNullable) {
            stringBuilder.append(" NOT NULL");
        }


        if (field.isAnnotationPresent(Id.class)) {
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
                if (annotation.strategy() == GenerationType.IDENTITY) {
                    stringBuilder.append(" AUTO_INCREMENT");
                }
            }
            stringBuilder.append(" PRIMARY KEY");
        }

        return stringBuilder.toString();
    }

    private ConcurrentHashMap<Class<?>, Integer> buildJavaClassToJdbcTypeCodeMappings() {
        final ConcurrentHashMap<Class<?>, Integer> workMap = new ConcurrentHashMap<>();

        workMap.put(String.class, Types.VARCHAR);
        workMap.put(Long.class, Types.BIGINT);
        workMap.put(Integer.class, Types.INTEGER);

        return workMap;
    }

    private String getColumnNameBySingleQuote(String columnName) {
        return "`" + columnName + "`";
    }

    private String getTypeName(Class<?> x) {
        return dialect.get(mappings.get(x));
    }
}
