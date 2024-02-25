package persistence.sql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.RowMapper;
import persistence.Person;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("nick_name");
        Integer age = resultSet.getInt("old");
        String email = resultSet.getString("email");
        return new Person(id, name, age, email, null);
    }
}
