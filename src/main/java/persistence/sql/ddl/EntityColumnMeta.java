package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static persistence.sql.ddl.DdlKeyGenerator.createPrimaryKeyGenerateDDL;
import static persistence.sql.dialect.JavaClassCodeTypeMappings.getJavaClassToJdbcCodeType;

public class EntityColumnMeta {

    public static final String SPACE = " ";
    public static final String COMMA = ",";
    private Dialect dialect;
    private Class<?> clazz;

    private EntityColumnMeta(final Class<?> clazz, final Dialect dialect) {
        this.dialect = dialect;
        this.clazz = clazz;
    }
    public static EntityColumnMeta of(final Class<?> clazz, final Dialect dialect) {
        return new EntityColumnMeta(clazz, dialect);
    }

    public String createColumnType() {
        String fieldDDLSql = createFieldDDLSql(clazz);
        final String constraintDDLSql = createConstraintDDLSql(clazz);

        return fieldDDLSql.concat(constraintDDLSql);
    }

    private String createFieldDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(f -> f.isAnnotationPresent(Id.class) ? 0 : 1))
                .filter(this::nonTransientField)
                .map(f -> {
                    String fieldName = createFieldName(f);

                    String printType = this.dialect.getColumnType(getJavaClassToJdbcCodeType(f.getType()));

                    String notNullDDL = createNotNullDDL(f);

                    String primaryKeyGenerateDDL = createPrimaryKeyGenerateDDL(this.dialect, f);

                    return String.format("%s %s%s%s", fieldName, printType, notNullDDL, primaryKeyGenerateDDL);
                })
                .collect(Collectors.joining(", "));
    }

    private String createConstraintDDLSql(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(f -> COMMA + createPrimaryKey(f))
                .collect(Collectors.joining());
    }

    private boolean nonTransientField(final Field field) {
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


    private String createPrimaryKey(final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return  String.format(" primary key (%s)", field.getName());
        }

        return "";
    }
}
