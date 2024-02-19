package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DDLGenerator {

    public static final String DOT = "\\.";

    public String generateCreate(Class<?> entity) {
        String tableName = getTableName(entity);
        String sql = "CREATE TABLE " + tableName + " (";

        sql += Arrays.stream(entity.getDeclaredFields())
                .map(field -> field.getName() + " " + getType(field))
                .collect(Collectors.joining(", "));

        String id = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(Field::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Persistent entity '%s' should have primary key", entity.getName())));

        sql += String.format(" CONSTRAINT %s_pk PRIMARY KEY(%s)", tableName, id);

        sql += ");";
        return sql;
    }

    private static String getTableName(Class<?> entity) {
        String name = entity.getName();

        String[] splitByDotName = name.split(DOT);

        return splitByDotName[splitByDotName.length - 1].toLowerCase();
    }

    private static String getType(Field field) {
        if (field.getType() == String.class) {
            return "VARCHAR";
        }

        if (field.getType() == Integer.class) {
            return "INT";
        }

        if (field.getType() == Long.class) {
            return "BIGINT";
        }

        if (field.getType() == LocalDate.class) {
            return "DATETIME";
        }

        if (field.getType() == Boolean.class) {
            return "BIT";
        }

        throw new IllegalStateException(String.format("Invalid field Type: %s", field.getType()));
    }
}
