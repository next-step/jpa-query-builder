package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    public String generateTableCreateDDL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        final StringBuilder queryBuilder = new StringBuilder();
        String tableName = getTableName(clazz);
        queryBuilder.append("CREATE TABLE ").append(tableName);
        queryBuilder.append(" (");

        final Field [] fields = clazz.getDeclaredFields();
        queryBuilder.append(String.join(", ", getCreateColumnQueries(fields)));

        queryBuilder.append(");");

        return queryBuilder.toString();
    }

    List<String> getCreateColumnQueries(Field [] fields) {
        return Arrays
                .stream(fields)
                .filter(x -> !x.isAnnotationPresent(Transient.class))
                .filter(x -> mappings.containsKey(x.getType()))
                .map(this::fieldToColumnQuery)
                .collect(Collectors.toList());
    }

    public String fieldToColumnQuery(Field field) {
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

        final StringBuilder stringBuilder = new StringBuilder();
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

    public String generateTableDropDDL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        return "DROP TABLE " + getTableName(clazz);
    }

    private String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            if (!annotation.name().isEmpty()) {
                tableName = annotation.name().toLowerCase();
            }
        }

        return tableName;
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
