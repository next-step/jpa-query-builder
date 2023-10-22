package persistence.mapper;

import entity.Person;
import jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Person(
                    rs.getLong("ID"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLD"),
                    rs.getString("EMAIL")
            );
        }
        return null;
    }
}
