package persistence.entity;

import domain.Person;
import jdbc.RowMapper;

import java.sql.ResultSet;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("nick_name");
            Integer age = resultSet.getInt("old");
            String email = resultSet.getString("email");
            return Person.of(id, name, age, email, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
