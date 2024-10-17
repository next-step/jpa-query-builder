package persistence.mapper;

import jdbc.RowMapper;
import persistence.sql.ddl.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong("id"),
                resultSet.getInt("age"),
                resultSet.getString("name")
        );
    }
}
