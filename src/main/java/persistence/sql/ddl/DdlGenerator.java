package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DdlGenerator {

    private final DBColumnTypeMapper columnTypeMapper;

    public DdlGenerator(final DBColumnTypeMapper columnTypeMapper) {
        this.columnTypeMapper = columnTypeMapper;
    }

    public String generateCreateDdl(final Class<?> clazz) {
        final StringBuilder builder = new StringBuilder();

        final String className = getTableNameBy(clazz);
        builder.append("create table ")
                .append(className)
                .append(" ")
                .append(generateColumnsStatement(clazz));

        return builder.toString();
    }

    private String generateColumnsStatement(final Class<?> clazz) {
        final StringBuilder builder = new StringBuilder();
        builder.append("(");

        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            final String fieldName = field.getName();
            final String columnName = columnTypeMapper.getColumnName(field.getType());

            builder.append(fieldName)
                    .append(" ")
                    .append(columnName)
                    .append(",");

        });

        builder.append(generatePKConstraintStatement(clazz));

        builder.append(")");
        return builder.toString();
    }

    private String generatePKConstraintStatement(final Class<?> clazz) {
        final StringBuilder builder = new StringBuilder();
        final Field idField = getIdField(clazz);
        final String className = getTableNameBy(clazz);
        builder.append("CONSTRAINT PK_")
                .append(className)
                .append(" PRIMARY KEY (")
                .append(idField.getName())
                .append(")");
        return builder.toString();
    }

    private Field getIdField(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    private String getTableNameBy(final Class<?> clazz) {
        return clazz.getSimpleName();
    }

}
