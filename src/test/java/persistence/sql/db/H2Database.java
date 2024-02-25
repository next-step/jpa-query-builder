package persistence.sql.db;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import persistence.entity.Person;
import persistence.sql.StandardDialectResolver;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.DialectResolutionInfo;
import persistence.sql.entity.EntityMappingTable;

import java.sql.SQLException;

public abstract class H2Database {

    protected static EntityMappingTable entityMappingTable;

    protected static DatabaseServer server;

    protected static JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setUpAll() throws SQLException {
        server = new H2();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        createTable();
    }

    private static void createTable() throws SQLException {
        DialectResolutionInfo dialectResolutionInfo = new DialectResolutionInfo(server.getConnection().getMetaData());
        Dialect dialect = StandardDialectResolver.resolveDialect(dialectResolutionInfo);
        entityMappingTable = EntityMappingTable.from(Person.class);
        CreateQueryBuilder createQueryBuilder = CreateQueryBuilder.of(entityMappingTable, dialect.getTypeMapper(), dialect.getConstantTypeMapper());

        jdbcTemplate.execute(createQueryBuilder.toSql());
    }

}
