package persistence;

import builder.QueryBuilderDDL;
import builder.h2.ddl.H2QueryBuilderDDL;
import builder.h2.dml.H2QueryBuilderDML;
import database.H2DBConnection;
import entity.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/*
- Persist 를 실행한다.
- find 를 실행한다.
- remove 실행한다.
- update 실행한다.
- update 실행할 시 존재하지 않은 데이터라면 예외를 발생시킨다.
*/
public class EntityManagerTest {

    private EntityManager em;
    private H2DBConnection h2DBConnection;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        this.h2DBConnection = new H2DBConnection();
        this.jdbcTemplate = this.h2DBConnection.start();

        //테이블 생성
        QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
        String createQuery = queryBuilderDDL.buildCreateQuery(Person.class);

        jdbcTemplate.execute(createQuery);

        this.em = new EntityManagerImpl(jdbcTemplate, new H2QueryBuilderDML());
    }

    //정확한 테스트를 위해 메소드마다 테이블 DROP 후 DB종료
    @AfterEach
    void tearDown() {
        QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
        String dropQuery = queryBuilderDDL.buildDropQuery(Person.class);
        jdbcTemplate.execute(dropQuery);
        this.h2DBConnection.stop();
    }

    @DisplayName("Persist를 실행한다.")
    @Test
    void persistTest() {
        Person person = createPerson(1);
        Person createdPerson = (Person) this.em.persist(person);

        assertThat(createdPerson)
                .extracting("id", "name", "age", "email")
                .contains(1L, "test1", 29, "test@test.com");
    }

    @DisplayName("find를 실행한다.")
    @Test
    void findTest() {
        Person person = createPerson(1);
        this.em.persist(person);

        Person findPerson = this.em.find(Person.class, person.getId());

        assertThat(findPerson)
                .extracting("id", "name", "age", "email")
                .contains(1L, "test1", 29, "test@test.com");
    }

    @DisplayName("remove 실행한다.")
    @Test
    void removeTest() {
        Person person = createPerson(1);
        this.em.persist(person);
        this.em.remove(person);

        assertThatThrownBy(() -> this.em.find(Person.class, person.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Expected 1 result, got 0");
    }

    @DisplayName("update 실행한다.")
    @Test
    void updateTest() {
        Person person = createPerson(1);
        this.em.persist(person);

        person.changeEmail("changed@test.com");
        this.em.update(person);

        Person findPerson = this.em.find(Person.class, person.getId());

        assertThat(findPerson)
                .extracting("id", "name", "age", "email")
                .contains(1L, "test1", 29, "changed@test.com");
    }

    @DisplayName("update 실행할 시 존재하지 않은 데이터라면 예외를 발생시킨다.")
    @Test
    void updateThrowExceptionTest() {
        Person person = createPerson(1);

        assertThatThrownBy(() -> this.em.update(person))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("데이터가 존재하지 않습니다. : Person");
    }

    private Person createPerson(int i) {
        return new Person((long) i, "test" + i, 29, "test@test.com");
    }
}
