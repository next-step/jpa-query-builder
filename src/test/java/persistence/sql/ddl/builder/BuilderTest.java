package persistence.sql.ddl.builder;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import persistence.meta.MetaEntity;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.fixture.PersonFixtureStep3;
import persistence.sql.fixture.PersonInstances;

import java.sql.SQLException;

public class BuilderTest {
  public static DatabaseServer server;
  public static Class<PersonFixtureStep3> person;
  public static JdbcTemplate jdbcTemplate;
  public static MetaEntity<PersonFixtureStep3> meta;
  public static CreateQueryBuilder createQueryBuilder;
  public static InsertQueryBuilder insertQueryBuilder;
  @BeforeAll
  static void setup() throws SQLException {
    person = PersonFixtureStep3.class;
    meta = MetaEntity.of(person);
    server = new H2();
    server.start();
    jdbcTemplate = new JdbcTemplate(server.getConnection());

    insertQueryBuilder = new InsertQueryBuilder();
    createQueryBuilder = new CreateQueryBuilder();

    String query = createQueryBuilder.createCreateQuery(meta.getCreateClause());
    jdbcTemplate.execute(query);

    String queryFirst = insertQueryBuilder.createInsertQuery(meta.getTableName(), meta.getColumnClause(), meta.getValueClause(PersonInstances.첫번째사람));
    String querySecond = insertQueryBuilder.createInsertQuery(meta.getTableName(), meta.getColumnClause(), meta.getValueClause(PersonInstances.두번째사람));
    jdbcTemplate.execute(queryFirst);
    jdbcTemplate.execute(querySecond);
  }
}
