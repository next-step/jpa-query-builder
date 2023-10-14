package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DdlGenerator {

    public String generateCreateDdl(final Class<?> clazz) {
        final StringBuilder builder = new StringBuilder();

        final String className = getTableNameBy(clazz);
        builder.append("create table ")
                .append(className)
                .append(" (");

        final Field[] declaredFields = clazz.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            field.setAccessible(true);
            final Class<?> type = field.getType();
            final String fieldName = field.getName();
            final String typeName = getDBColumnTypeFrom(type);

            builder.append(fieldName)
                    .append(" ")
                    .append(typeName)
                    .append(",");

        });

        final Field idField = getIdField(declaredFields);
        builder.append("CONSTRAINT PK_")
                .append(className)
                .append(" PRIMARY KEY (")
                .append(idField.getName())
                .append("))");

        return builder.toString();
    }

    private Field getIdField(final Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    private String getDBColumnTypeFrom(final Class<?> type) {
        final String typeName;
        if (type.isAssignableFrom(Long.class)) {
            typeName = "bigint";
        } else if (type.isAssignableFrom(String.class)) {
            typeName = "varchar";
        } else if (type.isAssignableFrom(Integer.class)) {
            typeName = "int";
        } else {
            throw new IllegalArgumentException("타입 맵핑 정보가 존재하지 않습니다.");
        }
        return typeName;
    }

    private String getTableNameBy(final Class<?> clazz) {
        return clazz.getSimpleName();
    }

}
