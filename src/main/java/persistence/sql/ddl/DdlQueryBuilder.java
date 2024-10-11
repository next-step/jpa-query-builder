package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.model.meta.DataType;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DdlQueryBuilder {
    private final Dialect dialect;

    public DdlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String getCreateTableQuery(Class<?> entityClass) {
        String CREATE_TABLE = "CREATE TABLE";
        return CREATE_TABLE + " " +
                dialect.getIdentifierQuoted(getTableName(entityClass)) +
                " (" +
                getColumnsDdl(entityClass) +
                ", " +
                getPrimaryDdl(entityClass) +
                ");";
    }

    private String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    private String getColumnsDdl(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .map(field -> {
                    String columnName = dialect.getIdentifierQuoted(field.getName());
                    String columnType = DataType.getByJavaType(field.getType()).getDefaultName();
                    String columnNull = dialect.getNullString(isColumnNullable(field));
                    return columnName + " " + columnType + " " + columnNull;
                })
                .collect(Collectors.joining(", "));
    }

    private String getPrimaryDdl(Class<?> entityClass) {
        String PRIMARY_KEY_FORMAT = "PRIMARY KEY (%s)";
        Field primaryField = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();

        return String.format(PRIMARY_KEY_FORMAT, dialect.getIdentifierQuoted(primaryField.getName()));
    }

    private Boolean isColumnNullable(Field field) {
        return !field.isAnnotationPresent(Id.class);
    }
}
