package persistence.sql.ddl.builder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.ddl.builder.DropQueryBuilder;
import persistence.dialect.h2.H2Dialect;
import persistence.meta.MetaData;
import persistence.sql.fixture.PersonFixture;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("1.2 Entity를 DROP DLL로 변환합니다.")
public class DropQueryBuilderTest {
  private static Class<PersonFixture> person;
  private static H2Dialect h2Dialect;
  private DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();

  @BeforeAll
  static void setup() {
    person = PersonFixture.class;
    h2Dialect = new H2Dialect();
  }

  @Test
  @DisplayName("1.2.4.1 @Entity, @id가 표기된 class의 TABLE DROP DDL을 생성합니다.")
  public void dropDdlFromEntityClass() {
    MetaData meta = MetaData.of(person, h2Dialect);

    String query = dropQueryBuilder.createDropQuery(meta);

    assertThat(query).isEqualTo("DROP TABLE USERS;");

  }
}
