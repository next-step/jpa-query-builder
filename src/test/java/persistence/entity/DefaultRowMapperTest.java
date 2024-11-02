package persistence.entity;

import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import java.sql.Types;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class DefaultRowMapperTest {

    @DisplayName("ResultSet을 받아 객체를 생성한다")
    @Test
    void mapRow() throws Exception {
        DefaultRowMapper<Person> defaultRowMapper = new DefaultRowMapper<>(Person.class);
        SimpleResultSet resultSet = createPersonResultSet(new Person(1L, "bob", 32, "test@email.com", 1));

        resultSet.next();
        Person person = defaultRowMapper.mapRow(resultSet);

        assertSoftly(softly -> {
            softly.assertThat(person.getId()).isEqualTo(1L);
            softly.assertThat(person.getName()).isEqualTo("bob");
            softly.assertThat(person.getAge()).isEqualTo(32);
            softly.assertThat(person.getEmail()).isEqualTo("test@email.com");
        });
    }

    private SimpleResultSet createPersonResultSet(Person person) {
        SimpleResultSet resultSet = new SimpleResultSet();

        resultSet.addColumn("id", Types.BIGINT, 19, 0);
        resultSet.addColumn("nick_name", Types.VARCHAR, 255, 0);
        resultSet.addColumn("old", Types.INTEGER, 10, 0);
        resultSet.addColumn("email", Types.VARCHAR, 255, 0);

        resultSet.addRow(
                person.getId(),
                person.getName(),
                person.getAge(),
                person.getEmail()
        );

        return resultSet;
    }
}
