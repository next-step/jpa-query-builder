package persistence.sql.dml;

import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setId(rs.getLong("id"));
        person.setName(rs.getString("nick_name"));
        person.setAge(rs.getInt("old"));
        person.setEmail(rs.getString("email"));
        return person;
    }
}
