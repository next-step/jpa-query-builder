package persistence.sql.dml.insert;

import jdbc.RowMapper;
import persistence.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        Person p = new Person(resultSet.getString("nick_name"), resultSet.getInt("old"), resultSet.getString("email"));
        p.setId(resultSet.getLong("id"));
        return p;
    }
}
