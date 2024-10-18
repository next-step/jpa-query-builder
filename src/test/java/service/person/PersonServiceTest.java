package service.person;

import builder.ddl.DDLBuilder;
import builder.ddl.DDLType;
import builder.ddl.h2.H2DDLBuilder;
import builder.dml.h2.H2DMLBuilder;
import database.H2DBConnection;
import entity.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.EntityManagerImpl;
import service.person.request.PersonRequest;
import service.person.response.PersonResponse;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
- Person 데이터를 전부 가져온다.
- Person 1L 데이터를 가져온다.
- Person 데이터를 가져올 시 존재하지 않는 데이터면 RuntimeException 이 발생한다.
- Person 1L 데이터를 삭제한다.
*/
public class PersonServiceTest {

    private PersonService personService;
    private H2DBConnection h2DBConnection;
    private JdbcTemplate jdbcTemplate;

    //정확한 테스트를 위해 메소드마다 DB실행 후 테이블생성
    @BeforeEach
    void eachSetUp() throws SQLException {
        this.h2DBConnection = new H2DBConnection();

        this.jdbcTemplate = this.h2DBConnection.start();

        //테이블 생성
        DDLBuilder ddlBuilder = new H2DDLBuilder();
        String createQuery = ddlBuilder.queryBuilder(DDLType.CREATE, Person.class);

        jdbcTemplate.execute(createQuery);

        this.personService = new PersonService(new EntityManagerImpl(jdbcTemplate, new H2DMLBuilder()));

        this.personService.save(createPersonRequest(1));
        this.personService.save(createPersonRequest(2));
    }

    //정확한 테스트를 위해 메소드마다 테이블 DROP 후 DB종료
    @AfterEach
    void tearDown() {
        DDLBuilder ddlBuilder = new H2DDLBuilder();
        String dropQuery = ddlBuilder.queryBuilder(DDLType.DROP, Person.class);
        jdbcTemplate.execute(dropQuery);
        this.h2DBConnection.stop();
    }

    @DisplayName("Person 데이터를 전부 가져온다.")
    @Test
    void findAllTest() {
        List<PersonResponse> personList = personService.findAll();

        assertThat(personList)
                .extracting("id", "name", "age", "email")
                .containsExactly(
                        tuple(1L, "test1", 29, "test@test.com"),
                        tuple(2L, "test2", 29, "test@test.com")
                );
    }

    @DisplayName("Person 1L 데이터를 가져온다.")
    @Test
    void findByIdTest() {
        PersonResponse personResponse = personService.findById(1L);

        assertThat(personResponse)
                .extracting("id", "name", "age", "email")
                .containsExactly(1L, "test1", 29, "test@test.com");
    }

    @DisplayName("Person 데이터를 가져올 시 존재하지 않는 데이터면 RuntimeException 이 발생한다.")
    @Test
    void findByIdThrowExceptionTest() {
        assertThatThrownBy(() -> personService.findById(3L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Expected 1 result, got 0");
    }

    @DisplayName("Person 1L 데이터를 삭제한다.")
    @Test
    void deleteByIdTest() {
        personService.deleteById(1L);
        List<PersonResponse> personResponseList = personService.findAll();

        assertThat(personResponseList)
                .extracting("id", "name", "age", "email")
                .containsExactly(
                        tuple(2L, "test2", 29, "test@test.com")
                );
    }

    private PersonRequest createPersonRequest(int i) {
        return new PersonRequest((long) i, "test" + i, 29, "test@test.com");
    }
}
