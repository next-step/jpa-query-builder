package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.person.SelectPerson;
import persistence.sql.ddl.QueryDdl;
import persistence.sql.dml.QueryDml;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityManagerImplTest {

    private Class<SelectPerson> selectPerson = SelectPerson.class;

    private EntityManager<SelectPerson> entityManager;
    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() throws SQLException {
        server = new H2();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(QueryDdl.create(selectPerson));

        entityManager = new EntityManagerImpl(server.getConnection(), selectPerson) {
            @Override
            public Object mapRow(ResultSet resultSet) {
                return null;
            }
        };
    }

    @Nested
    @DisplayName("데이터를 조회하는 find()")
    class find {
        @Test
        @DisplayName("정상적으로 DB에서 단건 데이터를 조회해 옴")
        void success() {
            //given
            final Long id = 3L;
            final String name = "zz";
            final int age = 30;
            final String email = "zz";
            final Integer index = 1;

            SelectPerson request = new SelectPerson(id, name, age, email, index);
            데이터를_저장함(request);

            //when
            SelectPerson result = entityManager.find(selectPerson, 3L);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getId()).isEqualTo(id);
                softAssertions.assertThat(result.getName()).isEqualTo(name);
                softAssertions.assertThat(result.getAge()).isEqualTo(age);
                softAssertions.assertThat(result.getEmail()).isEqualTo(email);
                softAssertions.assertThat(result.getIndex()).isNotEqualTo(index);
            });
        }
        @Test
        @DisplayName("없는 ID 조회 시도시 오류")
        void notFound() {
            //when & then
            assertThrows(RuntimeException.class, () -> entityManager.find(selectPerson, -1L));
        }
    }

    @Nested
    @DisplayName("데이터를 저장하는 persist()")
    class persist {
        @Test
        @DisplayName("정상적으로 데이터를 저장합니다")
        void success() {
            //given
            final Long id = 5L;
            final String name = "zz";
            final int age = 30;
            final String email = "zz";
            final Integer index = 1;

            //when
            SelectPerson request = new SelectPerson(id, name, age, email, index);
            SelectPerson result = (SelectPerson) entityManager.persist(request);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getId()).isEqualTo(id);
                softAssertions.assertThat(result.getName()).isEqualTo(name);
                softAssertions.assertThat(result.getAge()).isEqualTo(age);
                softAssertions.assertThat(result.getEmail()).isEqualTo(email);
            });
        }
    }

    @Nested
    @DisplayName("데이터를 삭제하는 remove()")
    class remove {
        @Test
        @DisplayName("별도 조건 없이 전체 테이블 데이터 삭제")
        void success() {
            //given
            final Long id = 99L;
            final String name = "zz";
            final int age = 30;
            final String email = "zz";
            final Integer index = 1;

            SelectPerson request = new SelectPerson(id, name, age, email, index);
            데이터를_저장함(request);

            //when & then
            assertDoesNotThrow(() -> entityManager.remove(request));
        }

        @Test
        @DisplayName("없는 테이블의 데이터 삭제 시도시 오류")
        void notFoundTable() {
            //given
            Person request = new Person(99L, "zz", 30, "zz", 1);

            //when & then
            assertThrows(RuntimeException.class, () -> entityManager.remove(request));
        }
    }

    @AfterEach
    void after() {
        jdbcTemplate.execute(QueryDdl.drop(selectPerson));
    }

    private <T> void 데이터를_저장함(T t) {
        jdbcTemplate.execute(QueryDml.insert(t));
    }
}
