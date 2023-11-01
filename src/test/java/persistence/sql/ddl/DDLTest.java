package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import domain.PersonV1;
import domain.PersonV2;
import domain.PersonV3;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import persistence.dialect.Dialect;
import persistence.dialect.H2Dialect;
import persistence.sql.Query;
import sources.AnnotationBinder;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DDLTest {
    DatabaseServer server;
    final Dialect dialect = new H2Dialect();
    final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(dialect);
    final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(dialect);
    final AnnotationBinder annotationBinder = new AnnotationBinder(dialect);
    final MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws SQLException {
        this.server = new H2();
        server.start();
        this.jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @Test
    @DisplayName("요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기")
    void createTest1() {
        Query query = createQueryBuilder.queryForObject(new PersonV1());
        jdbcTemplate.execute(String.valueOf(query.getQuery()));
        Assertions.assertThat(String.valueOf(query.getQuery()))
                .isEqualTo("create table PersonV1 (id Long, name varchar(255) , age int )");
    }

    @Test
    @DisplayName("요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기")
    void createTest2() {
        Query query = createQueryBuilder.queryForObject(new PersonV2());
        jdbcTemplate.execute(String.valueOf(query.getQuery()));
        Assertions.assertThat(String.valueOf(query.getQuery()))
                .isEqualTo("create table PersonV2 (id LONG AUTO_INCREMENT PRIMARY KEY, nick_name varchar(255) , old int, email varchar(255)  not null )");
    }

    @Test
    @DisplayName("요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기")
    void createTest3() {
        Query query = createQueryBuilder.queryForObject(new PersonV3());
        jdbcTemplate.execute(String.valueOf(query.getQuery()));
        Assertions.assertThat(String.valueOf(query.getQuery()))
                .isEqualTo("create table users (id LONG AUTO_INCREMENT PRIMARY KEY, nick_name varchar(255) , old int, email varchar(255)  not null )");
    }

    @Test
    @DisplayName("요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기")
    void dropTest() {
        Query query = dropQueryBuilder.queryForObject(new PersonV3());
        jdbcTemplate.execute(String.valueOf(query.getQuery()));
        Assertions.assertThat(String.valueOf(query.getQuery()))
                .isEqualTo("drop table users");
    }

    @AfterAll
    void close() {
        server.stop();
    }

}
