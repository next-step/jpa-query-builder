package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class SelectAllDmlTest {

    @DisplayName("Person 객체를 select all 쿼리로 변환한다.")
    @Test
    void testSelectAllDml() {
        SelectAllDml selectAllDml = new SelectAllDml();
        String selectAll = selectAllDml.generate(Person.class, Database.MYSQL);

        assertThat("select id, nick_name, old, email from users").isEqualTo(selectAll);
    }


}
