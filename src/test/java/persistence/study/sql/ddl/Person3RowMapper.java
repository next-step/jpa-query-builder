package persistence.study.sql.ddl;

import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Person3RowMapper implements RowMapper<Person3> {
    @Override
    public Person3 mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("nick_name");
        Integer age = resultSet.getInt("old");
        String email = resultSet.getString("email");
        return new Person3(id, name, age, email);
    }
}
