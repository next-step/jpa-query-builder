package persistence.sql.dml.h2;

import domain.PersonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2InsertQueryTest {

    @Test
    @DisplayName("Person 객체를 위한 INSERT 쿼리를 생성한다.")
    void insert() {
        String expected = "INSERT INTO users"
                + " (nick_name, old, email)"
                + " VALUES ('고정완', 30, 'ghojeong@email.com')";
        assertThat(
                H2InsertQuery.build(PersonFixture.createPerson())
        ).isEqualTo(expected);
    }
}
