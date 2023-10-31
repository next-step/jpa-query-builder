package persistence.meta;

import jakarta.persistence.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.fixture.PersonFixture;
import persistence.sql.fixture.PersonFixture2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("1.2 MetaDataTable을 Clazz에서 추출합니다.")
public class MetaDataTableTest {
  private static Class<PersonFixture2> person2;
  private static Class<PersonFixture> person1;

  @BeforeAll
  static void setup() {
    person1 = PersonFixture.class;
    person2 = PersonFixture2.class;
  }

  @Test
  @DisplayName("1.2.1 Clazz의 클래스 명과 table의 명이 매칭 됩니다.")
  public void matchClazzNameandTableName() {

    MetaDataTable table = MetaDataTable.of(person2);

    assertAll(
            () -> assertThat(table.getName()).isEqualTo("PERSONFIXTURE2")
    );
  }

  @Test
  @DisplayName("1.2.3 @Table에 name 값을 추가하였을 때, metaDataTable을 생성 할 수 있다.")
  public void requireEntityAnnotation() {
    Table tableAnnotation = person1.getAnnotation(Table.class);

    MetaDataTable table = MetaDataTable.of(person1);

    assertAll(
            () -> assertThat(tableAnnotation.name()).isEqualTo("users"),
            () -> assertThat(table.getName()).isEqualTo("USERS")
    );
  }

}
