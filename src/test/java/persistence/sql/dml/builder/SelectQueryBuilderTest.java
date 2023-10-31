package persistence.sql.dml.builder;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.meta.MetaEntity;
import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.fixture.PersonFixtureStep3;
import persistence.sql.fixture.PersonInstances;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2. 요구사항 SELECT 구현하기, 3. 요구사항 WHERE 구현하기")
public class SelectQueryBuilderTest {

  private static DatabaseServer server;
  private static Class<PersonFixtureStep3> person;
  private static JdbcTemplate jdbcTemplate;
  private static MetaEntity<PersonFixtureStep3> meta;

  @BeforeAll
  static void setup() throws SQLException {
    person = PersonFixtureStep3.class;
    server = new H2();
    server.start();
    jdbcTemplate = new JdbcTemplate(server.getConnection());

    meta = MetaEntity.of(person);
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
  @DisplayName("SELECT SQL 구문을 생성합니다.")
  public void selectDMLfromEntity() {
    SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();

    String query = selectQueryBuilder.createSelectQuery("id,nick_name,old,email", "USERS");

    assertThat(query).isEqualTo("SELECT id,nick_name,old,email FROM USERS;");
  }

  @Test
  @DisplayName("SELECT SQL 구문을 Where 문과 함께 생성합니다.")
  public void selectDMLWithWherefromEntity() {
    SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
    Long targetValue = 1L;

    String query = selectQueryBuilder.createSelectByFieldQuery(meta.getColumnClauseWithId(), meta.getTableName(), meta.getPrimaryKeyColumn().getSimpleName(), targetValue);

    assertThat(query).isEqualTo("SELECT id,nick_name,old,email FROM USERS WHERE id=1;");
  }

  @Test
  @DisplayName("Select 쿼리 실행시에 Entity들이 반환됩니다.")
  public void selectDMLfromEntityWhereDatabase() {
    SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
    Long targetValue = 1L;

    String query = selectQueryBuilder.createSelectByFieldQuery(meta.getColumnClauseWithId(), meta.getTableName(), meta.getPrimaryKeyColumn().getSimpleName(), targetValue);

    List<Object> people = jdbcTemplate.query(query, (rs) ->
            new PersonFixtureStep3(
                    rs.getLong("id"),
                    rs.getString("nick_name"),
                    rs.getInt("old"),
                    rs.getString("email")
            ));
    assertThat(people).contains(PersonInstances.첫번째사람);

  }
}
