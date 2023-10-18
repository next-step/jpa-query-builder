package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.ResultMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.exception.InvalidEntityException;
import persistence.person.InsertPerson;
import persistence.person.NotEntityPerson;
import persistence.person.SelectPerson;
import persistence.sql.ddl.QueryDdl;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryDmlTest {
    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void start() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @Nested
    @DisplayName("insert query")
    class insert {
        Class<InsertPerson> aClass = InsertPerson.class;
                
        @Test
        @DisplayName("@Entity가 설정되어 있지 않은 경우 Query를 생성하지 않음")
        void notEntity() {
            //given
            NotEntityPerson person = new NotEntityPerson(1L, "name", 3);

            //when & then
            assertThrows(InvalidEntityException.class, () -> QueryDml.insert(person));
        }

        @Test
        @DisplayName("@Entity가 설정되어 있지 않은 경우 Query를 생성하지 않음")
        void Success() {
            //given
            final Long id = 33L;
            final String name = "name";
            final int age = 22;

            NotEntityPerson person = new NotEntityPerson(id, name, age);

            //when
            String query = QueryDml.insert(person);

            //then
            assertDoesNotThrow(() -> jdbcTemplate.execute(query));
        }

        @AfterEach
        void after() {
            dropTable(aClass);
        }
    }

    @Nested
    @DisplayName("select query")
    class selectQuery {
        Class<SelectPerson> selectPersonClass = SelectPerson.class;

        @BeforeEach
        void init() {
            createTable(selectPersonClass);
        }

        @Test
        @DisplayName("현재 메소드를 읽어 조회하고자 하는 필드를 읽어 select문 실행하여 여러건 가져옴")
        void success() {
            //given
            final Long id = 1L;
            final String name = "홍길동";
            final int age = 20;
            final String email = "zzz";
            final Integer index = 4;

            final SelectPerson person = new SelectPerson(id, name, age, email, index);

            insert(person);

            //when
            List<SelectPerson> personList = jdbcTemplate.query(getSelectQuery(SelectPerson.class, "findAll"), new ResultMapper<>(SelectPerson.class));
            SelectPerson result = personList.get(0);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getId()).isEqualTo(person.getId());
                softAssertions.assertThat(result.getName()).isEqualTo(person.getName());
                softAssertions.assertThat(result.getAge()).isEqualTo(person.getAge());
                softAssertions.assertThat(result.getEmail()).isEqualTo(person.getEmail());
                softAssertions.assertThat(result.getIndex()).isNull();
            });
        }

        @Test
        @DisplayName("현재 메소드를 읽어 조회하고자 하는 필드를 읽어 select문 실행하여 여러건 가져옴")
        void successList() {
            //given
            final SelectPerson person1 = new SelectPerson(2L, "홍길동", 30, "zㅋ", 2);
            final SelectPerson person2 = new SelectPerson(3L, "김갑돌", 30, "zㅋ", 2);

            insert(person1);
            insert(person2);

            //when
            List<SelectPerson> personList = jdbcTemplate.query(getSelectQuery(SelectPerson.class, "findAll"), new ResultMapper<>(SelectPerson.class));

            //then
            assertThat(personList).size().isEqualTo(2);
        }

        @Test
        @DisplayName("@Entity가 존재하지 않는 클래스의 select query 생성시 오류 출력")
        void notEntity() {
            //given
            final Class<NotEntityPerson> aClass = NotEntityPerson.class;
            final String methodName = "findAll";

            //when & then
            assertThrows(InvalidEntityException.class,
                    () -> jdbcTemplate.query(getSelectQuery(aClass, methodName), new ResultMapper<>(SelectPerson.class)));
        }
    }

    private <T> String getSelectQuery(Class<T> tClass, String methodName) {
        return QueryDml.select(tClass, methodName);
    }

    private <T> void createTable(Class<T> tClass) {
        jdbcTemplate.execute(QueryDdl.create(tClass));
    }

    private <T> void insert(T t) {
        jdbcTemplate.execute(QueryDml.insert(t));
    }

    private <T> void dropTable(Class<T> tClass) {
        jdbcTemplate.execute(QueryDdl.drop(tClass));
    }

    @AfterEach
    void stop() {
        server.stop();
    }
}
