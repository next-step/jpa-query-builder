package domain;

import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        return new Person()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("nick_name"))
                .setAge(resultSet.getInt("old"))
                .setEmail(resultSet.getString("email"));
    }
}
