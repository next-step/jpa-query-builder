package persistence.mapper;

import jdbc.RowMapper;
import persistence.fixture.TestEntityFixture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestEntityRowMapper implements RowMapper<List<TestEntityFixture.SampleOneWithValidAnnotation>> {
    @Override
    public List<TestEntityFixture.SampleOneWithValidAnnotation> mapRow(ResultSet rs) throws SQLException {
        List<TestEntityFixture.SampleOneWithValidAnnotation> entities = new ArrayList<>();

        while (rs.next()) {
            entities.add(new TestEntityFixture.SampleOneWithValidAnnotation(
                    rs.getLong("ID"),
                    rs.getString("NAME"),
                    rs.getInt("OLD")
            ));
        }

        return entities;
    }
}
