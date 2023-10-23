package persistence.sql.meta;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.h2.H2Dialect;
import persistence.meta.MetaData;
import persistence.sql.fixture.PersonFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("1.2 MetaData를 Clazz에서 추출합니다.")
public class MetaDataTest {
  private static Class<PersonFixture> person;
  private static H2Dialect h2Dialect;
  final private String id = "id";
  final private String name = "name";
  final private String age = "age";
  final private String email = "email";
  @BeforeAll
  static void setup() {
    person = PersonFixture.class;
    h2Dialect = new H2Dialect();
  }
  @Test
  @DisplayName("1.2.1 Clazz가 주어졌을때, H2 Dialect에 맞게 CREATE TABLE을 만들 수 있다.")
  public void createH2TableFromEntity() {
    MetaData metaData = MetaData.of(person, h2Dialect);

    String query = metaData.generateQuery();


    assertAll( {
            () -> assertThat(query).isEqualTo("email");
    });
  }
  @Test
  @DisplayName("1.2.4 Clazz가 주어졌을때, H2 Dialect에 맞게 DROP TABLE을 만들 수 있다.")
  public void dropH2TableFromEntity() {
    MetaData metaData = MetaData.of(person, h2Dialect);

    String query = metaData.generateQuery();

    assertAll( {
      () -> assertThat(query).isEqualTo("email");
    });

  }
}
