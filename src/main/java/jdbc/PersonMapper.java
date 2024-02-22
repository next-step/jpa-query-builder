package jdbc;

import persistence.sql.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email"),
                0
        );
    }
}
