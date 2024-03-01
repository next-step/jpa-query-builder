package persistence.sql.ddl;

import domain.EntityMetaData;
import domain.H2GenerationType;
import domain.dialect.Dialect;
import domain.vo.JavaMappingType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static domain.Constraints.NOT_NULL;
import static domain.Constraints.NULL;
import static domain.Constraints.PRIMARY_KEY;

public class DdlQueryBuilder {

    private final JavaMappingType javaMappingType;
    private final Dialect dialect;
    private final EntityMetaData entityMetaData;

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s ( %s );";
    private static final String DROP_TABLE_QUERY = "DROP TABLE %s IF EXISTS;";
    private static final String COMMA = ", ";

    public DdlQueryBuilder(Dialect dialect, EntityMetaData entityMetaData) {
        this.javaMappingType = new JavaMappingType();
        this.dialect = dialect;
        this.entityMetaData = entityMetaData;
    }

    public String createTable(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !isTransientField(field))
                .forEach(field -> {
                    sb.append(getFieldInfo(field));
                    sb.append(COMMA);
                });

        String result = sb.toString().replaceAll(",[\\s,]*$", "");
        return String.format(CREATE_TABLE_QUERY, entityMetaData.getTableName(), result);
    }

    public String dropTable() {
        return String.format(DROP_TABLE_QUERY, entityMetaData.getTableName());
    }

    private boolean isTransientField(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String getFieldInfo(Field field) {
        if (entityMetaData.isIdField(field)) {
            return Stream.of(
                            entityMetaData.getFieldName(field), getFieldType(field),
                            NOT_NULL.getName(), PRIMARY_KEY.getName(), getGenerationType(field))
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));
        }

        return Stream.of(
                        entityMetaData.getFieldName(field), getFieldType(field),
                        getFieldLength(field), getColumnNullConstraint(field))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

    public String getFieldType(Field field) {
        Integer javaTypeByClass = javaMappingType.getJavaTypeByClass(field.getType());
        return dialect.getColumnDefine(javaTypeByClass);
    }

    public String getFieldLength(Field field) {
        return Objects.nonNull(getColumnLength(field)) ? "(" + getColumnLength(field) + ")" : null;
    }

    public String getGenerationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return H2GenerationType.from(field.getAnnotation(GeneratedValue.class).strategy()).getStrategy();
        }
        return null;
    }

    public String getColumnNullConstraint(Field field) {
        if (!isColumnField(field) || field.getAnnotation(Column.class).nullable()) {
            return NULL.getName();
        }
        return NOT_NULL.getName();
    }

    private boolean isColumnField(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private String getColumnLength(Field field) {
        if (isColumnField(field) && isVarcharType(field)) {
            return String.valueOf(field.getAnnotation(Column.class).length());
        }

        if (isColumnField(field) && !isVarcharType(field)) {
            return getLengthOrDefaultValue(field, 255);
        }

        return null;
    }

    private boolean isVarcharType(Field field) {
        Integer javaTypeByClass = javaMappingType.getJavaTypeByClass(field.getType());
        return javaTypeByClass.equals(Types.VARCHAR);
    }

    private String getLengthOrDefaultValue(Field field, int defaultLengthValue) {
        return field.getAnnotation(Column.class).length() == defaultLengthValue ? null
                : String.valueOf(field.getAnnotation(Column.class).length());
    }
}
