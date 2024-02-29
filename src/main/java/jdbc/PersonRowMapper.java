package jdbc;

import domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    public Person mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("nick_name");
        int old = resultSet.getInt("old");
        String email = resultSet.getString("email");
        return new Person(id, name, old, email);
    }
}
