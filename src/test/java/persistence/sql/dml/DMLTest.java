package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import persistence.dialect.Dialect;
import persistence.dialect.H2Dialect;
import persistence.sql.Query;
import persistence.sql.ddl.CreateQueryBuilder;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DMLTest {

    DatabaseServer server;
    final Dialect dialect = new H2Dialect();
    final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(dialect);
    final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
    final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(dialect);
    final AnnotationBinder annotationBinder = new AnnotationBinder();
    final MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws SQLException {
        this.server = new H2();
        server.start();
        this.jdbcTemplate = new JdbcTemplate(server.getConnection());
        MetaData person = metadataGenerator.generator(Person.class);
        StringBuilder sb = new StringBuilder();
        Query query = createQueryBuilder.create(person, sb);
        jdbcTemplate.execute(String.valueOf(query.getQuery()));
    }

    @Test
    @DisplayName("요구사항 1 - 위의 정보를 바탕으로 insert 구현해보기")
    void insert() {
        Person kim = new Person().name("김쿼리").age(30).email("query@gmail.com").index(1).build();
        Query insertQuery = insertQueryBuilder.insert(kim);
        jdbcTemplate.execute(insertQuery.getQuery().toString());
        Assertions.assertThat(String.valueOf(insertQuery.getQuery()))
                .isEqualTo("insert into users(nick_name, old, email ) values('김쿼리', 30, 'query@gmail.com' )");
    }

    @Test
    @DisplayName("요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기")
    void findAll() {
        Person kim = new Person().name("김쿼리").age(30).email("query1@gmail.com").index(1).build();
        Person lee = new Person().name("이쿼리").age(31).email("query2@gmail.com").index(1).build();
        Person lim = new Person().name("임쿼리").age(32).email("query3@gmail.com").index(1).build();
        insertPerson(kim);
        insertPerson(lee);
        insertPerson(lim);
        Query all = selectQueryBuilder.findAll(Person.class);
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> personList = jdbcTemplate.query(all.getQuery().toString(), personGenericRowMapper);
        System.out.println(personList.toString());
        assertAll(() -> assertEquals(isEqualPerson(personList.get(0), kim), Boolean.TRUE),
                () -> assertEquals(isEqualPerson(personList.get(1), lee), Boolean.TRUE),
                () -> assertEquals(isEqualPerson(personList.get(2), lim), Boolean.TRUE));

    }

    @Test
    @DisplayName("요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기")
    void findById() {
        Person kim = new Person().name("김쿼리").age(30).email("query1@gmail.com").index(1).build();
        Person lee = new Person().name("이쿼리").age(31).email("query2@gmail.com").index(1).build();
        Person lim = new Person().name("임쿼리").age(32).email("query3@gmail.com").index(1).build();
        insertPerson(kim);
        insertPerson(lee);
        insertPerson(lim);
        Query findByIdQuery = selectQueryBuilder.findById(Person.class, 1L);
        System.out.println(findByIdQuery.getQuery().toString());
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> personList = jdbcTemplate.query(findByIdQuery.getQuery().toString(), personGenericRowMapper);
        assertAll(() -> assertEquals(isEqualPerson(personList.get(0), kim), Boolean.TRUE));

    }

    @Test
    @DisplayName("요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기")
    void delete() {
        Person kim = new Person().name("김쿼리").age(30).email("query1@gmail.com").index(1).build();
        insertPerson(kim);
    }

    private void insertPerson(Person person) {
        Query insert = insertQueryBuilder.insert(person);
        jdbcTemplate.execute(insert.getQuery().toString());
    }

    private boolean isEqualPerson(Person entity, Person db) {
        return entity.getName().equals(db.getName()) && entity.getAge().equals(db.getAge()) && entity.getEmail().equals(db.getEmail());
    }
}
