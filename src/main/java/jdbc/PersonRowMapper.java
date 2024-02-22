package jdbc;

import persistence.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setName(resultSet.getString("nick_name"));
        person.setAge(resultSet.getInt("old"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }
}
