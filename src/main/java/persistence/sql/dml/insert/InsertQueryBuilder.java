package persistence.sql.dml.insert;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InsertQueryBuilder {
    private InsertQueryBuilder() {
    }

    public static String generateQuery(Object entity) throws IllegalAccessException {
        String tableName = NameUtils.getTableName(entity.getClass());
        String columnClause = columnClause(entity.getClass());
        String valueClause = valueClause(entity);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("insert into {TABLE_NAME} ")
                .append(columnClause)
                .append(" values ")
                .append(valueClause)
                .append(";");
        return stringBuilder.toString().replace("{TABLE_NAME}", tableName);
    }

    private static String columnClause(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder("(");

        Field[] managedFields = getManagedFields(clazz);
        for (Field field : managedFields) {
            stringBuilder
                    .append(NameUtils.getColumnName(field))
                    .append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    private static String valueClause(Object object) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder("(");

        Class<?> clazz = object.getClass();
        Field[] fields = getManagedFields(clazz);
        for (Field field : fields) {
            field.setAccessible(true);
            stringBuilder
                    .append(field.get(object))
                    .append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static Field[] getManagedFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(InsertQueryBuilder::isManagedField)
                .toArray(Field[]::new);
    }

    private static boolean isManagedField(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }
        if (field.isAnnotationPresent(Id.class)
                && field.isAnnotationPresent(GeneratedValue.class)
                && GenerationType.IDENTITY.equals(field.getAnnotation(GeneratedValue.class).strategy())) {
            return false;
        }
        return true;
    }
}
