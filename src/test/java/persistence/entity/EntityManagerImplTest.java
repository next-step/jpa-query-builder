package persistence.entity;

import database.DatabaseServer;
import database.H2;
import example.entity.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.EntityScanner;

import java.sql.SQLException;

public class EntityManagerImplTest {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImplTest.class);

    @Test
    @DisplayName("USERS 테이블 생성 > 데이터 저장 > 조회 테스트")
    void persistAndFindAndRemoveTest() throws SQLException {
        final DatabaseServer server = new H2();
        server.start();

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        EntityManagerImpl<Person, Long> entityManagerImpl = new EntityManagerImpl<>(jdbcTemplate);

        EntityScanner entityScanner = new EntityScanner();
        entityScanner.scan("example.entity");
        entityScanner.getDdlCreateQueries().forEach(jdbcTemplate::execute);

        Person inserting = new Person();
        inserting.setName("이름");
        inserting.setAge(15);
        inserting.setEmail("이메일@이메일.이메일");

        entityManagerImpl.persist(inserting);

        Person found = entityManagerImpl.find(Person.class, 1L);

        logger.debug("Found : {}", found);

        entityManagerImpl.remove(found);
    }

    @Test
    @DisplayName("USERS 테이블 생성 > 데이터 저장 > 조회 테스트")
    void persistAndUpdateTest() throws SQLException {
        final DatabaseServer server = new H2();
        server.start();

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        EntityManagerImpl<Person, Long> entityManagerImpl = new EntityManagerImpl<>(jdbcTemplate);

        EntityScanner entityScanner = new EntityScanner();
        entityScanner.scan("example.entity");
        entityScanner.getDdlCreateQueries().forEach(jdbcTemplate::execute);

        Person inserting = new Person();
        inserting.setName("이름");
        inserting.setAge(15);
        inserting.setEmail("이메일@이메일.이메일");

        entityManagerImpl.persist(inserting);

        Person found = entityManagerImpl.find(Person.class, 1L);

        logger.debug("Found : {}", found);

        found.setName("뉴이름");
        found.setAge(1500);
        found.setEmail("뉴이메일@뉴이메일.뉴이메일");

        entityManagerImpl.persist(found);

        Person reFound = entityManagerImpl.find(Person.class, 1L);

        logger.debug("Re found : {}", reFound);
    }
}
