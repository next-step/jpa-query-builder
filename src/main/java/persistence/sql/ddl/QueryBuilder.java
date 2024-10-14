package persistence.sql.ddl;

import jakarta.persistence.*;
import jdbc.JdbcTemplate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;

public class QueryBuilder {
    Class<Person> personClass = Person.class;

    public void Builder(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE " + getTableName() + "(" + getPersonMetadata() + ")");
    }

    public void Dropper(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DROP TABLE " + getTableName() + " IF EXISTS");
    }

    @NotNull
    private String getTableName() {
        if (personClass.isAnnotationPresent(Table.class)) {
            return personClass.getAnnotation(Table.class).name();
        }
        return personClass.getSimpleName();
    }


    public String getPersonMetadata() {
        String query = "";
        int count = 0;
        for (Field field : personClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Transient.class)) continue;

            query += getColumnName(field) + " " + getSqlType(field) + getPrimaryKey(field) + getAutoIncrement(field) + getNullable(field);
            count++;
            if (Arrays.stream(personClass.getDeclaredFields()).count() - 1 != count) {
                query += ", ";
            }
        }
        return query;
    }

    private String getNullable(Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable()) {
            return " NOT NULL";
        }
        return "";
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isEmpty()) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    private static String getAutoIncrement(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class) == true) {
            return " AUTO_INCREMENT";
        }
        return "";
    }

    private static String getPrimaryKey(Field field) {
        if (field.isAnnotationPresent(Id.class) == true) {
            return " PRIMARY KEY";
        }
        return "";
    }

    @NotNull
    private static String getSqlType(Field field) {
        if ("Long".equals(field.getType().getSimpleName())) {
            return "BIGINT";
        } else if ("String".equals(field.getType().getSimpleName())) {
            return "VARCHAR(255)";
        }
        return field.getType().getSimpleName();
    }

}