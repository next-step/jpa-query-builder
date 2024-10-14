package persistence.sql.ddl;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.util.Arrays;

public class PersonTest {
    Class<Person> personClass = Person.class;
    private static final Logger logger = LoggerFactory.getLogger(PersonTest.class);

    @Test
    void getTableName() {
        logger.debug(String.valueOf(personClass.getAnnotation(Table.class).name()));
    }

    @Test
    void getPersonMetadata() throws NoSuchFieldException {
        String query = "";
        int count = 0;

        for (Field field : personClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            query += getColumnName(field) + " " + getSqlType(field) + getPrimaryKey(field) + getAutoIncrement(field) + getNullable(field);
            count++;
            if (Arrays.stream(personClass.getDeclaredFields()).count() - 1 != count) {
                query += ", ";
            }
        }
        logger.debug(query);
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

    @Test
    void getColumnAnnotationValue() throws NoSuchFieldException {
        Field field1 = personClass.getDeclaredField("name");
        Column column = field1.getAnnotation(Column.class);
        String columnName = column.name();
        System.out.println(columnName);
    }
}

