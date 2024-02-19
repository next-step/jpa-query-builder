package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class QueryBuilder {

    public String generateCreate(Class<?> clazz) {
        return String.format("""
                create table %s
                (
                %s
                %s
                );
                """, getTableName(clazz), getColumns(clazz), getPK(clazz));
    }

    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    private String getPK(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Field pkField = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("PK must exists"));
        return String.format("    primary key (%s)", pkField.getName());
    }

    private String getColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return new H2Columns(fields).generateSQL();
    }

}
