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
    final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(dialect);
    final AnnotationBinder annotationBinder = new AnnotationBinder(dialect);
    final MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws SQLException {
        this.server = new H2();
        server.start();
        this.jdbcTemplate = new JdbcTemplate(server.getConnection());
        MetaData person = metadataGenerator.generator(Person.class);
        StringBuilder sb = new StringBuilder();
        Query query = createQueryBuilder.queryForObject(person, sb);
        jdbcTemplate.execute(String.valueOf(query.getQuery()));
    }

    @Test
    @DisplayName("요구사항 1 - 위의 정보를 바탕으로 insert 구현해보기")
    void insert() {
        Person kim = new Person().name("김쿼리").age(30).email("query@gmail.com").index(1).build();
        Query insertQuery = insertQueryBuilder.queryForObject(kim);
        jdbcTemplate.execute(insertQuery.getQuery().toString());
        Assertions.assertThat(String.valueOf(insertQuery.getQuery()))
                .isEqualTo("insert into users(nick_name, old, email ) values('김쿼리', 30, 'query@gmail.com' )");
    }

    @Test
    @DisplayName("요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기")
    void findAll() {
        Person lee = new Person().name("이쿼리").age(31).email("query2@gmail.com").index(1).build();
        Person lim = new Person().name("임쿼리").age(32).email("query3@gmail.com").index(1).build();
        insertPerson(lee);
        insertPerson(lim);

        Query all = selectQueryBuilder.findAll(Person.class);
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> personList = jdbcTemplate.query(all.getQuery().toString(), personGenericRowMapper);
        Assertions.assertThat(personList).contains(lee, lim);

    }

    @Test
    @DisplayName("요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기")
    void findById() {
        Person park = new Person().name("박쿼리").age(30).email("query4@gmail.com").index(1).build();
        insertPerson(park);

        Query all = selectQueryBuilder.findAll(Person.class);
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> personList = jdbcTemplate.query(all.getQuery().toString(), personGenericRowMapper);
        Person PersonPark = personList.get(personList.size() - 1);
        Query findByIdQuery = selectQueryBuilder.findById(Person.class, PersonPark.getId());
        List<Person> results = jdbcTemplate.query(findByIdQuery.getQuery().toString(), personGenericRowMapper);
        assertAll(() -> assertEquals(isEqualPerson(results.get(0), PersonPark), Boolean.TRUE));

    }

    @Test
    @DisplayName("요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기")
    void delete() throws NoSuchFieldException, IllegalAccessException {
        Person kim = new Person().name("김쿼리").age(30).email("query1@gmail.com").index(1).build();
        insertPerson(kim);
        Query findByIdQuery = selectQueryBuilder.findById(Person.class, 1L);
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> beforeDelete = jdbcTemplate.query(findByIdQuery.getQuery().toString(), personGenericRowMapper);

        Query delete = deleteQueryBuilder.queryForObject(beforeDelete.get(0));
        jdbcTemplate.execute(delete.getQuery().toString());

        Query all = selectQueryBuilder.findAll(Person.class);
        List<Person> afterDelete = jdbcTemplate.query(all.getQuery().toString(), personGenericRowMapper);
        assertEquals(afterDelete.size(), 0);

    }

    private void insertPerson(Person person) {
        Query insert = insertQueryBuilder.queryForObject(person);
        jdbcTemplate.execute(insert.getQuery().toString());
    }

    private boolean isEqualPerson(Person entity, Person db) {
        return entity.equals(db);
    }
}
