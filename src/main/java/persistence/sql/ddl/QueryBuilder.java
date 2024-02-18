package persistence.sql.ddl;

import persistence.domain.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class QueryBuilder {

    public static final String SQUARE_BRACKETS_OPEN = "(";
    public static final String SQUARE_BRACKETS_CLOSE = ")";
    public static final String CREATE_DEFAULT_DDL = "create table %s %s";
    public static final String SPACE = " ";
    public static final String COMMA = ",";

    public String createDdl(final Class<?> clazz) {
        String tableName = createTableName(clazz);

        final String defaultCreateDDL = String.format(CREATE_DEFAULT_DDL, tableName, SQUARE_BRACKETS_OPEN);
        StringBuilder ddl = new StringBuilder(defaultCreateDDL);

        final String fieldDDLSql = createFieldDDLSql(clazz);
        ddl.append(fieldDDLSql);

        final String constraintDDLSql = createConstraintDDLSql(clazz);
        ddl.append(constraintDDLSql);

        ddl.append(SQUARE_BRACKETS_CLOSE);

        return ddl.toString();
    }

    public String dropDdl(final Class<?> clazz) {
        final String tableName = createTableName(clazz);
        return String.format("drop table if exists %s CASCADE", tableName);
    }

    private String createTableName(final Class<?> clazz) {
        checkEntityAnnotationPresent(clazz);

        if (clazz.isAnnotationPresent(Table.class)) {
           return clazz.getAnnotation(Table.class).name();
        }

        return clazz.getSimpleName().toLowerCase();
    }

    private void checkEntityAnnotationPresent(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException();
        }
    }

    private String createFieldDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(f -> f.isAnnotationPresent(Id.class) ? 0 : 1))
                .filter(f -> !f.isAnnotationPresent(Transient.class))
                .map(f -> {
                    String fieldName = createFieldName(f);

                    String printType = DataType.ofPrintType(f);

                    String notNullDDL = createNotNullDDL(f);

                    String primaryKeyGenerateDDL = createPrimaryKeyGenerateDDL(f);

                    return String.format("%s %s%s%s", fieldName, printType, notNullDDL, primaryKeyGenerateDDL);
                })
                .collect(Collectors.joining(", "));
    }

    private String createFieldName(final Field f) {
        String fieldName = f.getName();

        if (f.isAnnotationPresent(Column.class) && isFieldNameNotBlank(f)) {
            fieldName = f.getAnnotation(Column.class).name();
        }

        return fieldName;
    }

    private String createNotNullDDL(final Field f) {
        if (f.isAnnotationPresent(Column.class) && isNotNullColumn(f)) {
            return SPACE + "not null";
        }

        return "";
    }

    private boolean isNotNullColumn(final Field f) {
        return !f.getAnnotation(Column.class).nullable();
    }

    private boolean isFieldNameNotBlank(final Field f) {
        return !f.getAnnotation(Column.class).name().isBlank();
    }

    private String createPrimaryKeyGenerateDDL(final Field f) {
        if (f.isAnnotationPresent(Id.class)) {
            return createGenerateTypeDDL(f);
        }

        return "";
    }

    private String createGenerateTypeDDL(final Field f) {
        if (f.isAnnotationPresent(GeneratedValue.class)) {
            return f.getAnnotation(GeneratedValue.class).strategy().generateDDL;
        }

        return SPACE + "not null";
    }

    private String createConstraintDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(f -> COMMA + createPrimaryKey(f))
                .collect(Collectors.joining());
    }

    private String createPrimaryKey(final Field f) {
        if (f.isAnnotationPresent(Id.class)) {
            return  String.format(" primary key (%s)", f.getName());
        }

        return "";
    }

}
