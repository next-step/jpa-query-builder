package service.person;

import builder.QueryBuilderDDL;
import builder.h2.dml.H2QueryBuilderDML;
import builder.h2.ddl.H2QueryBuilderDDL;
import database.H2DBConnection;
import entity.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.person.request.PersonRequest;
import service.person.response.PersonResponse;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/*
- Person 데이터를 전부 가져온다.
- Person 1L 데이터를 가져온다.
- Person 1L 데이터를 삭제한다.
- Person 1L 데이터를 가져온다.
- Person 데이터를 전체 가져온다.
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
        QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
        String createQuery = queryBuilderDDL.buildCreateQuery(Person.class);

        jdbcTemplate.execute(createQuery);

        this.personService = new PersonService(jdbcTemplate, new H2QueryBuilderDML());

        this.personService.save(createPersonRequest(1));
        this.personService.save(createPersonRequest(2));
    }

    //정확한 테스트를 위해 메소드마다 테이블 DROP 후 DB종료
    @AfterEach
    void tearDown() {
        QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
        String dropQuery = queryBuilderDDL.buildDropQuery(Person.class);
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

    @DisplayName("Person 데이틀을 전체 삭제한다.")
    @Test
    void deleteTest() {
        personService.deleteAll();
        List<PersonResponse> personResponseList = personService.findAll();

        assertThat(personResponseList).hasSize(0);
    }

    private PersonRequest createPersonRequest(int i) {
        return new PersonRequest((long) i, "test" + i, 29, "test@test.com");
    }
}
