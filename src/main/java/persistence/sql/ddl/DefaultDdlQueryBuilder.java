package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import persistence.sql.QueryException;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DefaultDdlQueryBuilder<T> implements DdlQueryBuilder<T> {

    private static final String SPACE = "    ";

    private final Dialect dialect;

    public DefaultDdlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String buildCreateQuery(final T entity) {

        final Class<?> clazz = getEntityClass(entity);

        final StringBuilder ddl = new StringBuilder();

        final String tableName = createTableName(clazz);

        ddl.append("CREATE TABLE ")
                .append(tableName)
                .append(" (\n")
                .append(SPACE);

        final String columns = Arrays.stream(clazz.getDeclaredFields())
                .map(this::generateColumnQuery)
                .collect(Collectors.joining(",\n" + SPACE));

        ddl.append(columns)
                .append("\n")
                .append(");");

        return ddl.toString();
    }

    private Class<?> getEntityClass(final T entity) {
        final Class<?> clazz = entity.getClass();

        if (!clazz.isAnnotationPresent(Entity.class)) throw new QueryException(clazz.getSimpleName() + " is not entity");

        return clazz;
    }

    private String generateColumnQuery(final Field field) {
        final String columnName = createColumnName(field);
        final String typeName = field.getType().getSimpleName();
        final boolean isPk = field.isAnnotationPresent(Id.class);
        final String columnType = dialect.createColumnQuery(typeName);

        final StringBuilder columnQuery = new StringBuilder()
                .append(columnName)
                .append(" ")
                .append(columnType);

        if (isPk) columnQuery.append(" ")
                .append(dialect.getPk());

        return columnQuery.toString();
    }

    private String createTableName(final Class<?> clazz) {
        return clazz.getSimpleName().toUpperCase();
    }

    private String createColumnName(final Field field) {
        return field.getName().toUpperCase();
    }

}
