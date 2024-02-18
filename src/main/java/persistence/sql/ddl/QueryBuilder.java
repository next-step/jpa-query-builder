package persistence.sql.ddl;

import persistence.domain.Entity;
import persistence.domain.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class QueryBuilder {

    public static final String SQUARE_BRACKETS_OPEN = "(";
    public static final String SQUARE_BRACKETS_CLOSE = ")";
    public static final String SPACE = " ";
    public static final String COMMA = ",";

    public String createDdl(final Class<?> clazz) {
        final String defaultCreateDDL = String.format("create table %s %s", clazz.getSimpleName().toLowerCase(), SQUARE_BRACKETS_OPEN);
        StringBuilder ddl = new StringBuilder(defaultCreateDDL);

        checkEntityAnnotationPresent(clazz);

        final String fieldDDLSql = createFieldDDLSql(clazz);

        ddl.append(fieldDDLSql);

        final String primaryDDLSql = createPrimaryDDLSql(clazz);
        ddl.append(primaryDDLSql);

        ddl.append(SQUARE_BRACKETS_CLOSE);

        return ddl.toString();
    }

    private String createFieldDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(f -> f.isAnnotationPresent(Id.class) ? 0 : 1))
                .map(f -> {
                    final String printType = DataType.ofPrintType(f);

                    String notNullDDL = createNotNull(f);

                    return String.format("%s %s%s", f.getName(), printType, notNullDDL);
                })
                .collect(Collectors.joining(", "));
    }

    private String createPrimaryDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(f -> COMMA + createPrimaryKey(f))
                .collect(Collectors.joining());
    }

    private static void checkEntityAnnotationPresent(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException();
        }
    }

    private String createPrimaryKey(final Field f) {
        if (f.isAnnotationPresent(Id.class)) {
            return  String.format(" primary key (%s)", f.getName());
        }

        return "";
    }

    private String createNotNull(final Field f) {
        if (f.isAnnotationPresent(Id.class)) {
            return  SPACE + "not null";
        }

        return "";
    }
}
