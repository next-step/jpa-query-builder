package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fake.FakeDialect;
import persistence.sql.QueryGenerator;
import persistence.testFixtures.Person;

class InsertQueryBuilderTest {

    @Test
    @DisplayName("insert 쿼리를 생성한다.")
    void insert() {
        //given
        QueryGenerator<Person> query = QueryGenerator.from(Person.class);
        Person person = new Person("name", 3, "kbh@gm.com");

        //when
        String sql = query.insert(person);

        //then
        assertThat(sql).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('name', 3, 'kbh@gm.com')");
    }

    @Test
    @DisplayName("다른 방언으로 insert 쿼리를 생성한다.")
    void insertDirect() {
        //given
        QueryGenerator<Person> query = QueryGenerator.of(Person.class, new FakeDialect());
        Person person = new Person("name", 3, "kbh@gm.com");

        //when
        String sql = query.insert(person);

        //then
        assertThat(sql).isEqualTo("INSERT INTO USERS (nick_name, old, email) VALUES ('name', 3, 'kbh@gm.com')");
    }


}
