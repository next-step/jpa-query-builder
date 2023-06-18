package persistence.sql.dml.h2;

import domain.Person;
import domain.PersonFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.DmlBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class H2DmlBuilderTest {
    private DmlBuilder dml;

    @BeforeEach
    void setUp() {
        dml = H2DmlBuilder.getInstance();
    }

    @Test
    @DisplayName("Person 객체를 위한 INSERT 쿼리를 생성한다.")
    void insert() {
        String expected = "INSERT INTO users"
                + " (nick_name, old, email)"
                + " VALUES ('고정완', 30, 'ghojeong@email.com')";
        assertThat(
                dml.getInsertQuery(PersonFixture.createPerson())
        ).isEqualTo(expected);
    }

    @Test
    @DisplayName("Person Entity 를 위한 findAll 쿼리를 생성한다.")
    void findAll() {
        String expected = "SELECT"
                + " id, nick_name, old, email"
                + " FROM users";
        assertThat(
                dml.getFindAllQuery(Person.class)
        ).isEqualTo(expected);
    }

    @Test
    @DisplayName("Person Entity 를 위한 findById 쿼리를 생성한다.")
    void findById() {
        String expected = "SELECT"
                + " id, nick_name, old, email"
                + " FROM users"
                + " WHERE id = 1";
        assertThat(
                dml.getFindByIdQuery(Person.class, 1)
        ).isEqualTo(expected);
    }

    @Test
    @DisplayName("Person Entity 를 위한 delete 쿼리를 생성한다.")
    void delete() {
        String expected = "DELETE FROM users"
                + " WHERE id = 1";
        assertThat(
                dml.getDeleteByIdQuery(Person.class, 1)
        ).isEqualTo(expected);
    }
}
