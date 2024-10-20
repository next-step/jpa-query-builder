package persistence;

import builder.ddl.DDLBuilderData;
import builder.ddl.builder.CreateQueryBuilder;
import builder.ddl.builder.DropQueryBuilder;
import builder.ddl.dataType.DB;
import builder.ddl.dataType.H2DataType;
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
- Persist로 Person 저장 후 find로 조회한다.
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
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();
        String createQuery = queryBuilder.buildQuery(DDLBuilderData.createDDLBuilderData(Person.class, DB.H2));

        jdbcTemplate.execute(createQuery);

        this.em = new EntityManagerImpl(jdbcTemplate);
    }

    //정확한 테스트를 위해 메소드마다 테이블 DROP 후 DB종료
    @AfterEach
    void tearDown() {
        DropQueryBuilder queryBuilder = new DropQueryBuilder();
        String dropQuery = queryBuilder.buildQuery(DDLBuilderData.createDDLBuilderData(Person.class, DB.H2));
        jdbcTemplate.execute(dropQuery);
        this.h2DBConnection.stop();
    }

    @DisplayName("Persist로 Person 저장 후 find로 조회한다.")
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
