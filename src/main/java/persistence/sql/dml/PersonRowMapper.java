package persistence.sql.dml;

import jdbc.RowMapper;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs) {
        Person person = new Person();
        try {

            Field idField = person.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(person, rs.getLong("id"));

            Field nameField = person.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(person, rs.getString("nick_name"));

            Field ageField = person.getClass().getDeclaredField("age");
            ageField.setAccessible(true);
            ageField.set(person, rs.getInt("old"));

            Field emailField = person.getClass().getDeclaredField("email");
            emailField.setAccessible(true);
            emailField.set(person, rs.getString("email"));

        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
