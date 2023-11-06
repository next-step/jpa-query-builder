package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.entity.Person;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet) throws SQLException {
        Person p = new Person(resultSet.getString("nick_name"), resultSet.getInt("old"), resultSet.getString("email"));
        p.setId(resultSet.getLong("id"));
        return p;
    }
}
