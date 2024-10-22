package persistence.sql.dml.update;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UpdateQueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(UpdateQueryBuilder.class);

    private UpdateQueryBuilder() {
    }

    public static String generateQuery(Object entity) {
        String tableName = NameUtils.getTableName(entity.getClass());
        Field idColumnField = getIdColumnField(entity.getClass());
        String idColumnName = NameUtils.getColumnName(idColumnField);
        idColumnField.setAccessible(true);
        Object idValue;
        try {
            idValue = idColumnField.get(entity);
        } catch (IllegalAccessException e) {
            logger.error("Error while generating query!");
            throw new RuntimeException(e);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("update ")
                .append(tableName)
                .append(" set ");

        Map<String, String> nameToValueMap = getNameToValueMap(entity);

        nameToValueMap.entrySet().forEach(
                entry -> stringBuilder
                        .append(entry.getKey())
                        .append(" = '")
                        .append(entry.getValue())
                        .append("', ")
        );

        stringBuilder.setLength(stringBuilder.length() - 2);

        stringBuilder
                .append(" where ")
                .append(idColumnName)
                .append(" = '")
                .append(idValue)
                .append("';");

        return stringBuilder.toString();
    }

    private static Map<String, String> getNameToValueMap(Object entity) {
        Map<String, String> resultMap = new HashMap<>();

        Class<?> entityClass = entity.getClass();
        Field[] fields = getManagedFields(entityClass);
        for (Field field : fields) {
            String fieldName = NameUtils.getColumnName(field);
            field.setAccessible(true);
            String fieldValue = String.valueOf(getFieldValue(field, entity));
            resultMap.put(fieldName, fieldValue);
        }
        return resultMap;
    }

    private static Field[] getManagedFields(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(UpdateQueryBuilder::isManagedField)
                .toArray(Field[]::new);
    }

    private static Boolean isManagedField(Field field) {
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

    private static Object getFieldValue(Field field, Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            logger.error("Error while generating query!");
            throw new RuntimeException(e);
        }
    }

    private static Field getIdColumnField(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Inappropriate entity class!");
    }
}
