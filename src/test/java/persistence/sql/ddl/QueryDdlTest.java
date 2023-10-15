package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.person.NotEntityPerson;
import persistence.person.PersonOne;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        @DisplayName("정상적으로 Class 정보를 읽어 CREATE QUERY 생성하여 실행 성공")
        void success() {
            //given
            Class<PersonOne> personClass = PersonOne.class;

            //when
            String query = QueryDdl.create(personClass);

            //when & then
            assertDoesNotThrow(() -> jdbcTemplate.execute(query));
        }

        @Test
        @DisplayName("@Entity가 설정되어 있지 않은 경우 Query를 생성하지 않음")
        void notEntity() {
            //given
            Class<NotEntityPerson> personClass = NotEntityPerson.class;

            //when
            String query = QueryDdl.create(personClass);

            //then
            assertThat(query).isNull();
        }
    }

    @AfterEach
    void stop() {
        server.stop();
    }
}