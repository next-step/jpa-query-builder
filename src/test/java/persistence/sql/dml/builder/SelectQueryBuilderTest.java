package persistence.sql.dml.builder;

import static org.assertj.core.api.Assertions.assertThat;

import database.DatabaseServer;
import database.H2;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.h2.H2Dialect;
import persistence.meta.MetaData;
import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.fixture.PersonFixtureStep3;
import persistence.sql.fixture.PersonInstances;

@DisplayName("2. 요구사항 SELECT 구현하기, 3. 요구사항 WHERE 구현하기")
public class SelectQueryBuilderTest {

  private static DatabaseServer server;
  private static Class<PersonFixtureStep3> person;
  private static JdbcTemplate jdbcTemplate;

  @BeforeAll
  static void setup() throws SQLException {
    person = PersonFixtureStep3.class;
    server = new H2();
    server.start();

    MetaData meta = MetaData.of(person, new H2Dialect());
    CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();
    String query = createQueryBuilder.createCreateQuery(meta);

    jdbcTemplate = new JdbcTemplate(server.getConnection());
    jdbcTemplate.execute(query);

    InsertQueryBuilder<PersonFixtureStep3> insertQueryBuilder = new InsertQueryBuilder();
    String queryFirst = insertQueryBuilder.createInsertQuery(PersonInstances.첫번째사람);
    String querySecond = insertQueryBuilder.createInsertQuery(PersonInstances.두번째사람);
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
    SelectQueryBuilder<PersonFixtureStep3> selectQueryBuilder = new SelectQueryBuilder<>();

    String query = selectQueryBuilder.createSelectQuery(PersonInstances.첫번째사람);

    assertThat(query).isEqualTo("SELECT id,nick_name,old,email FROM USERS;");
  }

  @Test
  @DisplayName("SELECT SQL 구문을 Where 문과 함께 생성합니다.")
  public void selectDMLWithWherefromEntity() throws NoSuchFieldException, IllegalAccessException {
    SelectQueryBuilder<PersonFixtureStep3> selectQueryBuilder = new SelectQueryBuilder<>();
    Field field = PersonInstances.첫번째사람.getClass().getDeclaredField("id");
    field.setAccessible(true);
    field.set(PersonInstances.첫번째사람, 1L);

    String query = selectQueryBuilder.createSelectByFieldQuery(PersonInstances.첫번째사람, field);

    assertThat(query).isEqualTo("SELECT id,nick_name,old,email FROM USERS WHERE id=1;");
  }

  @Test
  @DisplayName("Select 쿼리 실행시에 Entity들이 반환됩니다.")
  public void selectDMLfromEntityDatabase()
      throws SQLException, NoSuchFieldException, IllegalAccessException {
    SelectQueryBuilder<PersonFixtureStep3> selectQueryBuilder = new SelectQueryBuilder<>();
    Field field = PersonInstances.첫번째사람.getClass().getDeclaredField("id");
    field.setAccessible(true);
    field.set(PersonInstances.첫번째사람, 1L);

    String query = selectQueryBuilder.createSelectByFieldQuery(PersonInstances.첫번째사람, field);

    assertThat(query).contains("SELECT id,nick_name,old,email FROM USERS WHERE id=1;");
  }

  @Test
  @DisplayName("Select 쿼리 실행시에 Entity들이 반환됩니다.")
  public void selectDMLfromEntityWhereDatabase()
      throws NoSuchFieldException, IllegalAccessException {
    SelectQueryBuilder<PersonFixtureStep3> selectQueryBuilder = new SelectQueryBuilder<>();
    Field field = PersonInstances.첫번째사람.getClass().getDeclaredField("id");
    field.setAccessible(true);
    field.set(PersonInstances.첫번째사람, 1L);

    String query = selectQueryBuilder.createSelectByFieldQuery(PersonInstances.첫번째사람, field);

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
