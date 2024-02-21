package persistence.sql.ddl;

import domain.DataType;
import domain.IdGenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;

import static domain.Constraints.NOT_NULL;
import static domain.Constraints.NULL;
import static domain.Constraints.PRIMARY_KEY;

public class H2QueryBuilderImpl implements H2QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s ( %s );";
    private static final String DROP_TABLE_QUERY = "DROP TABLE %s;";

    private static final String COMMA = ", ";
    private static final String SPACE = " ";
    private static final String DEFAULT_VALUE = "";

    @Override
    public String createDdl(Class<?> clazz) {
        checkEntityClass(clazz);

        StringBuilder sb = new StringBuilder();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !isTransientField(field))
                .forEach(field -> {
                    sb.append(createQueryByField(field));
                    sb.append(COMMA);
                });

        String result = sb.toString().replaceAll(",[\\s,]*$", DEFAULT_VALUE);
        return String.format(CREATE_TABLE_QUERY, getTableName(clazz), result);
    }

    @Override
    public String dropTable(Class<?> clazz) {
        return String.format(DROP_TABLE_QUERY, getTableName(clazz));
    }

    private void checkEntityClass(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("Entity 클래스가 아닙니다.");
        }
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    private String createQueryByField(Field field) {
        if (isTransientField(field)) {
            return SPACE;
        }

        if (isIdField(field)) {
            return String.join(SPACE, getFieldName(field), getDataType(field), NOT_NULL.getName(), PRIMARY_KEY.getName(),
                    getGenerationType(field));
        }
        return String.join(SPACE, getFieldName(field), getDataType(field), getColumnLength(field),
                getColumnNullConstraint(field));
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isColumnField(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private boolean isTransientField(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String getFieldName(Field field) {
        if (isColumnField(field)) {
            return isBlankOrEmpty(field.getAnnotation(Column.class).name()) ? field.getName() : field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    private boolean isBlankOrEmpty(String target) {
        return target.isBlank() || target.isEmpty();
    }

    private String getDataType(Field field) {
        return DataType.from(field.getType().getSimpleName()).getSqlDataType();
    }

    private String getGenerationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return IdGenerationType.from(field.getAnnotation(GeneratedValue.class).strategy()).getValue();
        }
        return DEFAULT_VALUE;
    }

    private String getColumnLength(Field field) {
        if (isColumnField(field) && isVarcharType(field)) {
            return "(" + field.getAnnotation(Column.class).length() + ")";
        }

        if (isColumnField(field) && !isVarcharType(field)) {
            return getLengthOrDefaultValue(field, 255);
        }

        return DEFAULT_VALUE;
    }

    private boolean isVarcharType(Field field) {
        return DataType.from(field.getType().getSimpleName()).isVarcharType();
    }

    private String getLengthOrDefaultValue(Field field, int defaultLengthValue) {
        return field.getAnnotation(Column.class).length() == defaultLengthValue ? DEFAULT_VALUE
                : "(" + field.getAnnotation(Column.class).length() + ")";
    }

    private String getColumnNullConstraint(Field field) {
        if (!isColumnField(field) || field.getAnnotation(Column.class).nullable()) {
            return NULL.getName();
        }
        return NOT_NULL.getName();
    }
}
