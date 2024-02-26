package domain.step3.mapper;

import domain.Person3;
import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person3> {

    @Override
    public Person3 mapRow(ResultSet resultSet) throws SQLException {
        return new Person3(
                resultSet.getLong("id"), resultSet.getString("nick_name"),
                resultSet.getInt("old"), resultSet.getString("email")
        );
    }
}
