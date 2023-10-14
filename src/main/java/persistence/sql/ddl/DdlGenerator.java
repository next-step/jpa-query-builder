package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DdlGenerator {

    private final DBColumnMapper columnMapper;

    public DdlGenerator(final DBColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
    }

    public String generateCreateDdl(final Class<?> clazz) {
        final StringBuilder builder = new StringBuilder();

        final String className = getTableNameBy(clazz);
        builder.append("create table ")
                .append(className)
                .append(" (");

        final Field[] declaredFields = clazz.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            field.setAccessible(true);
            final String fieldName = field.getName();
            final String columnName = columnMapper.getColumnName(field.getType());

            builder.append(fieldName)
                    .append(" ")
                    .append(columnName)
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

    private String getTableNameBy(final Class<?> clazz) {
        return clazz.getSimpleName();
    }

}
