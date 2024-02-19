package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.h2.TableName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    private final Class<?> clazz;

    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String generateCreate() {
        return String.format("""
                create table %s
                (
                %s
                %s
                );
                """, getTableName(clazz), getColumns(clazz), getPK(clazz));
    }

    public String generateDrop() {
        return String.format("drop table %s;", getTableName(clazz));
    }

    private String getTableName(Class<?> clazz) {
        return new TableName(clazz).getTableName();
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
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .toList();
        return new H2Columns(fields).generateSQL();
    }
}
