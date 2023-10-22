package hibernate.entity;

import database.DatabaseServer;
import database.H2;
import hibernate.ddl.CreateQueryBuilder;
import jakarta.persistence.*;
import jdbc.JdbcTemplate;
import jdbc.ReflectionRowMapper;
import jdbc.RowMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityManagerImplTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static EntityManagerImpl entityManager;
    private static final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();

    @BeforeAll
    static void beforeAll() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        entityManager = new EntityManagerImpl(jdbcTemplate);

        jdbcTemplate.execute(createQueryBuilder.generateQuery(new EntityClass<>(TestEntity.class)));
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("truncate table test_entity;");
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    @Test
    void 저장된_객채를_찾는다() {
        // given
        jdbcTemplate.execute("insert into test_entity (id, nick_name, age) values (1, '최진영', 19)");

        // when
        TestEntity actual = entityManager.find(TestEntity.class, 1L);

        // then
        assertAll(
                () -> assertThat(actual.id).isEqualTo(1L),
                () -> assertThat(actual.name).isEqualTo("최진영"),
                () -> assertThat(actual.age).isEqualTo(19)
        );
    }

    @Test
    void 객체를_저장한다() {
        // given
        TestEntity givenEntity = new TestEntity("최진영", 19, "jinyoungchoi95@gmail.com");

        // when
        entityManager.persist(givenEntity);
        TestEntity actual = jdbcTemplate.queryForObject("select id, nick_name, age from test_entity;", new ReflectionRowMapper<>(TestEntity.class));

        // then
        assertAll(
                () -> assertThat(actual.id).isEqualTo(1L),
                () -> assertThat(actual.name).isEqualTo(givenEntity.name),
                () -> assertThat(actual.age).isEqualTo(givenEntity.age)
        );
    }

    @Test
    void 객체를_제거한다() {
        // given
        TestEntity givenEntity = new TestEntity(1L, "최진영", 19, "jinyoungchoi95@gmail.com");
        entityManager.persist(givenEntity);

        // when
        entityManager.remove(givenEntity);
        Integer actual = jdbcTemplate.queryForObject("select count(*) from test_entity", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet) {
                try {
                    resultSet.next();
                    return resultSet.getInt(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // then
        assertThat(actual).isEqualTo(0);
    }

    @Entity
    @Table(name= "test_entity")
    static class TestEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name", nullable = false)
        private String name;

        private Integer age;

        @Transient
        private String email;

        public TestEntity() {
        }

        public TestEntity(Long id, String name, Integer age, String email) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public TestEntity(String name, Integer age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }
    }
}
