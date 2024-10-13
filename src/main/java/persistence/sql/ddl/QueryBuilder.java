package persistence.sql.ddl;

import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;

public class QueryBuilder {
    Class<Person> personClass = Person.class;

    public void Builder(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE " + personClass.getSimpleName() + "(" + getPersonMetadata() + ")" );
    }


    public String getPersonMetadata() {
        String query = "";
        int count = 0;
        for (Field field : personClass.getDeclaredFields()) {
            query += field.getName() + " " + getSqlType(field) + getPrimaryKey(field);
            count++;
            if (Arrays.stream(personClass.getDeclaredFields()).count() != count) {
                query += ", ";
            }
        }
        return query;
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