package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PersonTest {
    Class<Person> personClass = Person.class;
    private static final Logger logger = LoggerFactory.getLogger(PersonTest.class);

    @Test
    void Test() {
        logger.debug(personClass.getName()); //persistence.sql.ddl.Person
        logger.debug(personClass.getSimpleName()); // Person
        logger.debug(Arrays.toString(personClass.getDeclaredAnnotations())); //[@jakarta.persistence.Entity(name="")]
        logger.debug(Arrays.toString(personClass.getDeclaredFields())); //[private java.lang.Long persistence.sql.ddl.Person.id, private java.lang.String persistence.sql.ddl.Person.name, private java.lang.Integer persistence.sql.ddl.Person.age]
        logger.debug(Arrays.toString(personClass.getDeclaredConstructors())); //public persistence.sql.ddl.Person()]

        for (Field field : personClass.getDeclaredFields()) {
//            field.setAccessible(true);
            logger.debug(field.getType().getSimpleName());
            logger.debug(field.getName());
            logger.debug(String.valueOf(field.isAnnotationPresent(Id.class)));
        }
    }

    @Test
    void getPersonMetadata() {
        String query = "";
        int count = 0;
        for (Field field : personClass.getDeclaredFields()) {
            query += field.getName() + " " + getSqlType(field) + getPrimaryKey(field);
            count++;
            if (Arrays.stream(personClass.getDeclaredFields()).count() != count) {
                query += ", ";
            }
        }
        logger.debug(query);
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

