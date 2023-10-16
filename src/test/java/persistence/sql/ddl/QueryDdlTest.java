package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.person.NotEntityPerson;
import persistence.person.NotParseTypePerson;
import persistence.person.PersonThree;
import persistence.person.PersonTwo;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryDdlTest {

    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void start() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @Nested
    @DisplayName("요구사항1")
    class one {
        @Test
        @DisplayName("@Entity가 설정되어 있지 않은 경우 Query를 생성하지 않음")
        void notEntity() {
            //given
            Class<NotEntityPerson> personClass = NotEntityPerson.class;

            //when & then
            assertThrows(NullPointerException.class, () -> QueryDdl.create(personClass));
        }
    }

    @Nested
    @DisplayName("요구사항2")
    class two {
        @Test
        @DisplayName("정상적으로 Class 정보를 읽어 CREATE QUERY 생성하여 실행 성공")
        void success() {
            //given
            Class<PersonTwo> personClass = PersonTwo.class;

            //when
            String query = QueryDdl.create(personClass);

            //then
            assertDoesNotThrow(() -> jdbcTemplate.execute(query));
        }

        @Test
        @DisplayName("지원하지 않는 자료형 입력시 오류")
        void invalidType() {
            //given
            Class<NotParseTypePerson> personClass = NotParseTypePerson.class;

            //when & then
            assertThrows(IllegalArgumentException.class, () -> QueryDdl.create(personClass));
        }
    }

    @Nested
    @DisplayName("요구사항3")
    class three {
        @Test
        @DisplayName("정상적으로 Class 정보를 읽어 CREATE QUERY 생성하여 실행 성공")
        void success() {
            //given
            Class<PersonThree> personClass = PersonThree.class;

            //when
            String query = QueryDdl.create(personClass);

            //then
            assertDoesNotThrow(() -> jdbcTemplate.execute(query));
        }
    }

    @Nested
    @DisplayName("요구사항4")
    class four {
        @Test
        @DisplayName("정상적으로 Person 테이블 삭제")
        void success() {
            //given
            Class<Person> personClass = Person.class;
            createTable(personClass);

            //when
            String query = QueryDdl.drop(personClass);

            //then
            assertDoesNotThrow(() -> jdbcTemplate.execute(query));
        }

        @Test
        @DisplayName("@Entity가 없는 class는 drop query 생성하지 않는다.")
        void notMakeDropQuery() {
            //given
            Class<NotEntityPerson> personClass = NotEntityPerson.class;

            //when & then
            assertThrows(NullPointerException.class, () -> QueryDdl.drop(personClass));
        }
    }

    private <T> void createTable(Class<T> tClass) {
        jdbcTemplate.execute(QueryDdl.create(tClass));
    }

    @AfterEach
    void stop() {
        server.stop();
    }
}