package persistence.sql.ddl;


import jakarta.persistence.*;
import persistence.sql.ddl.domain.DdlKeyGenerator;
import persistence.sql.ddl.domain.H2DdlKeyGenerateor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DdlQueryBuilder {

    public static final String CREATE_DEFAULT_DDL = "create table %s (%s)";
    public static final String SPACE = " ";
    public static final String COMMA = ",";

    public String createDdl(final Class<?> clazz) {
        String tableName = createTableName(clazz);

        String fieldDDLSql = createFieldDDLSql(clazz, new H2DdlKeyGenerateor());

        final String constraintDDLSql = createConstraintDDLSql(clazz);

        return String.format(CREATE_DEFAULT_DDL, tableName, fieldDDLSql.concat(constraintDDLSql));
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

    private String createFieldDDLSql(final Class<?> clazz, final DdlKeyGenerator ddlKeyGenerator) {
        return Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(f -> f.isAnnotationPresent(Id.class) ? 0 : 1))
                .filter(DdlQueryBuilder::nonTransientField)
                .map(f -> {
                    String fieldName = createFieldName(f);

                    String printType = DataType.ofPrintType(f);

                    String notNullDDL = createNotNullDDL(f);

                    String primaryKeyGenerateDDL = createPrimaryKeyGenerateDDL(f, ddlKeyGenerator);

                    return String.format("%s %s%s%s", fieldName, printType, notNullDDL, primaryKeyGenerateDDL);
                })
                .collect(Collectors.joining(", "));
    }

    private static boolean nonTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private String createFieldName(final Field field) {
        String fieldName = field.getName();

        if (field.isAnnotationPresent(Column.class) && isFieldNameNotBlank(field)) {
            fieldName = field.getAnnotation(Column.class).name();
        }

        return fieldName;
    }

    private String createNotNullDDL(final Field field) {
        if (field.isAnnotationPresent(Column.class) && isNotNullColumn(field)) {
            return SPACE + "not null";
        }

        return "";
    }

    private boolean isNotNullColumn(final Field field) {
        return !field.getAnnotation(Column.class).nullable();
    }

    private boolean isFieldNameNotBlank(final Field field) {
        return !field.getAnnotation(Column.class).name().isBlank();
    }

    private String createPrimaryKeyGenerateDDL(final Field field, final DdlKeyGenerator ddlKeyGenerator) {
        if (field.isAnnotationPresent(Id.class)) {
            return createGenerateTypeDDL(field, ddlKeyGenerator);
        }

        return "";
    }

    private String createGenerateTypeDDL(final Field field, final DdlKeyGenerator ddlKeyGenerator) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return ddlKeyGenerator.generator(field.getAnnotation(GeneratedValue.class).strategy());
        }

        return SPACE + "not null";
    }

    private String createConstraintDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(f -> COMMA + createPrimaryKey(f))
                .collect(Collectors.joining());
    }

    private String createPrimaryKey(final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return  String.format(" primary key (%s)", field.getName());
        }

        return "";
    }

}
