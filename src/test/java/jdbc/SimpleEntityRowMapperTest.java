package jdbc;

import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.Dialect;
import persistence.sql.entitymetadata.model.EntityTable;
import persistence.testutils.ReflectionTestSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityRowMapperTest {

    @Test
    void rowMapper() throws SQLException {
        // given
        EntityTable<Person> entityTable = new EntityTable<>(Person.class);
        SimpleEntityRowMapper<Person> simpleEntityRowMapper = new SimpleEntityRowMapper<>(entityTable, Dialect.H2);

        ResultSet resultSet = new FakeResultSet(Map.of(
                "ID", 1L,
                "NICK_NAME", "thxwelchs",
                "OLD", 100,
                "EMAIL", "thxwelchs@gmail.com"
        ));

        Person person = simpleEntityRowMapper.mapRow(resultSet);

        assertThat(ReflectionTestSupport.getFieldValue(person, "id")).isEqualTo(1L);
        assertThat(ReflectionTestSupport.getFieldValue(person, "name")).isEqualTo("thxwelchs");
        assertThat(ReflectionTestSupport.getFieldValue(person, "age")).isEqualTo(100);
        assertThat(ReflectionTestSupport.getFieldValue(person, "email")).isEqualTo("thxwelchs@gmail.com");
    }

    static class FakeResultSet extends SimpleResultSet {
        private final Map<String, Object> fakeInMemoryData;

        public FakeResultSet(Map<String, Object> fakeInMemoryData) {
            super();
            this.fakeInMemoryData = fakeInMemoryData;
        }

        @Override
        public Object getObject(String columnLabel) {
            return fakeInMemoryData.get(columnLabel);
        }

        @Override
        public boolean next() {
            return true;
        }
    }
}
