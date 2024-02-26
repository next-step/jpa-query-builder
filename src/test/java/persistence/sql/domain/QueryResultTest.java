package persistence.sql.domain;

import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryResultTest {

    private final Class<Person> clazz = Person.class;

    @Test
    void should_return_single_entity() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DatabaseTable table = new DatabaseTable(Person.class);
        QueryResult queryResult = new QueryResult(new MockResultSet(), table);
        Person person = queryResult.getSingleEntity(Person.class);


        assertAll(
                () -> validateFieldValue("id", 1l, person),
                () -> validateFieldValue("name", "cs", person),
                () -> validateFieldValue("age", 29, person),
                () -> validateFieldValue("email", "katd216@gmail.com", person)
        );
    }

    private void validateFieldValue(String fieldName, Object fieldValue, Object instance) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        assertThat(field.get(instance)).isEqualTo(fieldValue);
    }

    private static class MockResultSet extends SimpleResultSet implements ResultSet {

        private final List<Map<String, Object>> rows = Collections.singletonList(Map.of(
                "id", 1l,
                "nick_name", "cs",
                "old", 29,
                "email", "katd216@gmail.com"
        ));
        private int pointer = -1;

        @Override
        public Object getObject(String columnLabel) {
            return rows.get(pointer).get(columnLabel);
        }

        @Override
        public boolean next() {
            boolean hasNext = pointer < rows.size() - 1;
            pointer++;
            return hasNext;
        }
    }
}
