package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DMLTest {

    DatabaseServer server;
    final Dialect dialect = new H2Dialect();
    final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(dialect);
    final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
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
        jdbcTemplate.execute(insertQuery.getQuery());
    }
}
