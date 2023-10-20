package persistence.sql.dml;


import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.Dialect;
import persistence.dialect.h2.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class DmlGeneratorTest {

    private DmlGenerator generator;
    private Person person;

    @BeforeEach
    void setUp() {
        final Dialect dialect = new H2Dialect();
        this.generator = new DmlGenerator(dialect);
        this.person = new Person(1L,"min", 30, "jongmin4943@gmail.com");
    }

    @Test
    @DisplayName("주어진 person 인스턴스를 이용해 insert ddl 을 생성할 수 있다.")
    void dmlGeneratorTest() {
        final String query = generator.insert(person);

        assertThat(query).isEqualToIgnoringCase(String.format("insert into users (nick_name, old, email) values ('%s', %d, '%s')", "min", 30, "jongmin4943@gmail.com"));
    }

    @Test
    @DisplayName("Person 클래스 정보로 select ddl 을 생성할 수 있다.")
    void findAllTest() {
        final String query = generator.findAll(Person.class);
        assertThat(query).isEqualToIgnoringCase("select id, nick_name, old, email from users");
    }

    @Test
    @DisplayName("Person 클래스 정보로 select ddl 을 생성할 수 있다.")
    void findByIdTest() {
        final String query = generator.findById(Person.class, 1L);
        assertThat(query).isEqualToIgnoringCase("select id, nick_name, old, email from users where id=1");
    }

    @Test
    @DisplayName("Person 클래스 정보로 delete ddl 을 생성할 수 있다.")
    void generateDeleteDmlTest() {
        final String query = generator.delete(person);
        assertThat(query).isEqualToIgnoringCase("delete from users where id=1");
    }

}
