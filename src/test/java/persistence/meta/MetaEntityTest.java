package persistence.meta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.h2.H2Dialect;
import persistence.sql.fixture.PersonFixtureStep3;
import persistence.sql.fixture.PersonInstances;

@DisplayName("1.2 MetaEntity를 Entity에서 추출합니다.")
public class MetaEntityTest {
  private static PersonFixtureStep3 person;
  private static H2Dialect h2Dialect;
  private static MetaEntity<PersonFixtureStep3> entity;

  @BeforeAll
  static void setup() {
    person = PersonInstances.첫번째사람;
    h2Dialect = new H2Dialect();
    entity = MetaEntity.of(person);
  }

  @Test
  @DisplayName("Entity가 주어졌을때, Table 이름을 가져올 수 있다.")
  public void getTableNameFromEntity() {
    String tabelName = entity.getTableName();

    assertAll(
        () -> assertThat(tabelName).isEqualTo("USERS")
    );
  }

  @Test
  @DisplayName("Entity가 주어졌을때, Id를 제외하고 Columns를 만들 수 있다.")
  public void getTableColumnsWithoutId() {
    List<String> columns = entity.getEntityColumns();

    assertAll(
        () -> assertThat(columns).containsExactly("nick_name","old","email")
    );
  }

  @Test
  @DisplayName("Entity가 주어졌을때, Id를 포함한 테이블의 컬럼 이름들을 가져 올 수 있다.")
  public void getTableColumnsWithId() {
    List<String> columns = entity.getEntityColumnsWithId();

    assertAll(
        () -> assertThat(columns).containsExactly("id","nick_name","old","email")
    );
  }

  @Test
  @DisplayName("Entity가 주어졌을때, class의 필드 이름들을 가져 올 수 있다.")
  public void createH2TableFromEntity() {
    List<String> columns = entity.getEntityFields();

    assertAll(
        () -> assertThat(columns).containsExactly("name","age","email")
    );
  }
}
