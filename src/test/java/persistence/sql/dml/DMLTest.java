package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import persistence.dialect.Dialect;
import persistence.dialect.H2Dialect;
import sources.AnnotationBinder;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DMLTest {

    DatabaseServer server;
    final Dialect dialect = new H2Dialect();
    final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
    final AnnotationBinder annotationBinder = new AnnotationBinder();
    final MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws SQLException {
        this.server = new H2();
        server.start();
        this.jdbcTemplate = new JdbcTemplate(server.getConnection());
    }
}
