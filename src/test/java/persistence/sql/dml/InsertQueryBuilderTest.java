package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {
    InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person.class);

    @Test
    @DisplayName("요구사항1: insert 쿼리 생성")
    void testGenerateInsertQuery() {
        String nickName = "nickName";
        int age = 20;
        String email = "email";
        Person person = new Person(null, nickName, age, email, null);
        String expected = String.format(
                "insert into users (nick_name, old, email) values ('%s', %s, '%s')",
                nickName,
                age,
                email
        );

        String insertQuery = insertQueryBuilder.toQuery(person);

        assertThat(insertQuery).isEqualTo(expected);
    }
}
