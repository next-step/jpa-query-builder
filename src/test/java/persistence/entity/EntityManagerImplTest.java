package persistence.entity;

import database.DatabaseServer;
import database.H2;
import example.entity.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.EntityScanner;

import java.sql.SQLException;

public class EntityManagerImplTest {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImplTest.class);

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static EntityScanner entityScanner;

    @BeforeEach
    void init() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());

        entityScanner = new EntityScanner();
        entityScanner.scan("example.entity");
        entityScanner.getDdlCreateQueries().forEach(jdbcTemplate::execute);
    }

    @AfterEach
    void teardown() {
        entityScanner.getDdlDropQueries().forEach(jdbcTemplate::execute);
        server.stop();
    }

    @Test
    @DisplayName("USERS 테이블 생성 > 데이터 저장 > 조회 테스트")
    void persistAndFindAndRemoveTest() {
        EntityManagerImpl entityManagerImpl = new EntityManagerImpl(jdbcTemplate);

        Person inserting = new Person();
        inserting.setName("이름");
        inserting.setAge(15);
        inserting.setEmail("이메일@이메일.이메일");

        entityManagerImpl.persist(inserting);

        Person found = (Person) entityManagerImpl.find(Person.class, 1L);

        logger.debug("Found : {}", found);

        entityManagerImpl.remove(found);
    }

    @Test
    @DisplayName("USERS 테이블 생성 > 데이터 저장 > 조회 테스트")
    void persistAndUpdateTest() {
        EntityManagerImpl entityManagerImpl = new EntityManagerImpl(jdbcTemplate);

        Person inserting = new Person();
        inserting.setName("이름");
        inserting.setAge(15);
        inserting.setEmail("이메일@이메일.이메일");

        entityManagerImpl.persist(inserting);

        Person found = (Person) entityManagerImpl.find(Person.class, 1L);

        logger.debug("Found : {}", found);

        found.setName("뉴이름");
        found.setAge(1500);
        found.setEmail("뉴이메일@뉴이메일.뉴이메일");

        entityManagerImpl.persist(found);

        Person reFound = (Person) entityManagerImpl.find(Person.class, 1L);

        logger.debug("Re found : {}", reFound);
    }
}
