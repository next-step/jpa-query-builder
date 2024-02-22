package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static domain.Constraints.NOT_NULL;
import static domain.Constraints.PRIMARY_KEY;

public class QueryBuilder {

    private final Dialect dialect;

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s ( %s );";
    private static final String DROP_TABLE_QUERY = "DROP TABLE %s;";
    private static final String COMMA = ", ";

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String createDdl(Class<?> clazz) {
        checkEntityClass(clazz);

        StringBuilder sb = new StringBuilder();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !isTransientField(field))
                .forEach(field -> {
                    sb.append(getFieldInfo(field));
                    sb.append(COMMA);
                });

        String result = sb.toString().replaceAll(",[\\s,]*$", "");
        return String.format(CREATE_TABLE_QUERY, dialect.getTableName(clazz), result);
    }

    public String dropTable(Class<?> clazz) {
        checkEntityClass(clazz);
        return String.format(DROP_TABLE_QUERY, dialect.getTableName(clazz));
    }

    private void checkEntityClass(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("Entity 클래스가 아닙니다.");
        }
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isTransientField(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String getFieldInfo(Field field) {
        if (isIdField(field)) {
            return Stream.of(
                            dialect.getFieldName(field), dialect.getFieldType(field),
                            NOT_NULL.getName(), PRIMARY_KEY.getName(), dialect.getGenerationType(field))
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));
        }

        return Stream.of(
                        dialect.getFieldName(field), dialect.getFieldType(field),
                        dialect.getFieldLength(field), dialect.getColumnNullConstraint(field))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }
}
