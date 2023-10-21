package persistence.mapper;

import jdbc.RowMapper;
import persistence.fixture.TestEntityFixture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestEntityRowMapper implements RowMapper<List<TestEntityFixture.EntityWithValidAnnotation>> {
    @Override
    public List<TestEntityFixture.EntityWithValidAnnotation> mapRow(ResultSet rs) throws SQLException {
        List<TestEntityFixture.EntityWithValidAnnotation> entities = new ArrayList<>();

        while (rs.next()) {
            entities.add(new TestEntityFixture.EntityWithValidAnnotation(
                    rs.getLong("ID"),
                    rs.getString("NAME"),
                    rs.getInt("OLD")
            ));
        }

        return entities;
    }
}
