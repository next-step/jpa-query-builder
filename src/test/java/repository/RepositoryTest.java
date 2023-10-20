package repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import database.DatabaseServer;
import database.H2;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import persistence.testFixtures.Person;


@DisplayName("테이블이 ")
public class RepositoryTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    @BeforeAll
    static void setUp() throws SQLException {
        final DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

    }


    @TestFactory
    @DisplayName("데이터 베이스를 관리한다.")
    Stream<DynamicNode> testFactory() {
        final DDLRepository ddlRepository = new DDLRepository(jdbcTemplate);
        final CrudRepository repository = new CrudRepository(jdbcTemplate);

        Person person = new Person("이름", 30, "email@odna");
        Person person2 = new Person("이름2", 32, "email2@odna");

        return Stream.of(
                dynamicContainer("테이블을 생성하면", Stream.of(
                        dynamicTest("테이블을 생성된다.", () -> {
                            assertDoesNotThrow(() -> ddlRepository.createTable(Person.class));
                        }))
                ),
                dynamicTest("2건이 저장된다.", () -> {
                    assertDoesNotThrow(() -> {
                        repository.save(Person.class, person);
                        repository.save(Person.class, person2);
                    });
                }),
                dynamicContainer("전체를 조회하면", Stream.of(
                        dynamicTest("2건이 조회된다.", () -> {
                            //when
                            final List<Person> persons = repository.findAll(Person.class);

                            //then
                            assertSoftly((it) -> {
                                it.assertThat(persons).hasSize(2);
                                it.assertThat(persons.get(0).getName()).isEqualTo(person.getName());
                                it.assertThat(persons.get(0).getAge()).isEqualTo(person.getAge());
                                it.assertThat(persons.get(0).getEmail()).isEqualTo(person.getEmail());
                                it.assertThat(persons.get(1).getName()).isEqualTo(person2.getName());
                                it.assertThat(persons.get(1).getAge()).isEqualTo(person2.getAge());
                                it.assertThat(persons.get(1).getEmail()).isEqualTo(person2.getEmail());
                            });
                        }))
                )
        );
    }

}
