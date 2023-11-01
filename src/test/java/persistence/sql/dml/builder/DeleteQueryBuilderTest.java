package persistence.sql.dml.builder;

import static org.assertj.core.api.Assertions.assertThat;

import database.DatabaseServer;
import database.H2;
import java.sql.SQLException;
import java.util.List;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.meta.MetaEntity;
import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.fixture.PersonFixtureStep3;
import persistence.sql.fixture.PersonInstances;

@DisplayName("4. 요구사항 DELETE 구현하기")
public class DeleteQueryBuilderTest {

  private static DatabaseServer server;
  private static Class<PersonFixtureStep3> person;
  private static JdbcTemplate jdbcTemplate;
  private static MetaEntity<PersonFixtureStep3> meta;
  @BeforeAll
  static void setup() throws SQLException {
    person = PersonFixtureStep3.class;
    meta = MetaEntity.of(person);
    server = new H2();
    server.start();
    jdbcTemplate = new JdbcTemplate(server.getConnection());

    InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
    CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();

    String query = createQueryBuilder.createCreateQuery(meta.getCreateClause());
    jdbcTemplate.execute(query);

    String queryFirst = insertQueryBuilder.createInsertQuery(meta.getTableName(), meta.getColumnClause(), meta.getValueClause(PersonInstances.첫번째사람));
    String querySecond = insertQueryBuilder.createInsertQuery(meta.getTableName(), meta.getColumnClause(), meta.getValueClause(PersonInstances.두번째사람));
    jdbcTemplate.execute(queryFirst);
    jdbcTemplate.execute(querySecond);
  }

  @AfterAll
  static void teardown() {
    server.stop();
  }

  @Test
  @DisplayName("Delete SQL 구문을 생성합니다.")
  public void deleteDMLfromEntity() {
    DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();

    String query = deleteQueryBuilder.createDeleteQuery("USERS", "id", 1L);

    assertThat(query).isEqualTo("DELETE FROM USERS WHERE id = 1;");
  }


  @Test
  @DisplayName("Delete SQL 구문을 실행합니다.")
  public void deleteDMLfromEntitySQL() {
    DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
    SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
    Long targetId = 1L;

    String queryDelete = deleteQueryBuilder.createDeleteQuery(meta.getTableName(), meta.getPrimaryKeyColumn().getSimpleName(), targetId);
    jdbcTemplate.execute(queryDelete);

    String querySelect = selectQueryBuilder.createSelectByFieldQuery(meta.getColumnClause(), meta.getTableName(), meta.getPrimaryKeyColumn().getSimpleName(), targetId);
    List<Object> people = jdbcTemplate.query(querySelect, (rs) ->
            new PersonFixtureStep3(
                    rs.getLong("id"),
                    rs.getString("nick_name"),
                    rs.getInt("old"),
                    rs.getString("email")
            ));

    assertThat(people).isEmpty();
  }
}
