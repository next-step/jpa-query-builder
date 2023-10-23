package persistence.sql.meta;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.h2.H2Dialect;
import persistence.meta.MetaDataColumn;
import persistence.meta.MetaDataColumns;
import persistence.sql.fixture.PersonFixture;
import persistence.sql.fixture.PersonFixture2;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("1.2 MetaDataColumns를 Clazz에서 추출합니다.")
public class MetaDataColumnsTest {
  private static Class<PersonFixture2> personWithoutTransient;
  private static Class<PersonFixture> personWithTransient;
  private static H2Dialect h2Dialect;
  final private String id = "id";
  final private String name = "name";
  final private String age = "age";
  final private String email = "email";
  final private String index = "index";
  @BeforeAll
  static void setup() {
    personWithoutTransient = PersonFixture.class;
    personWithTransient = PersonFixture2.class;
    h2Dialect = new H2Dialect();
  }
  @Test
  @DisplayName("1.2.1 Column clause를 생성합니다.")
  public void createColumnsDdl() {
    List<MetaDataColumn> dataColumnList = new ArrayList<>();
    for (Field declaredField : personWithoutTransient.getDeclaredFields()){
      Type type = h2Dialect.convertType(declaredField);
      dataColumnList.add(MetaDataColumn.of(declaredField, type));
    }
    MetaDataColumns dataColumns = MetaDataColumns.of(dataColumnList);

    assertThat(dataColumnList.getColumnClause()).isEqualTo("");
  }
  @Test
  @DisplayName("1.2.3 Transient 애노테이션일 경우 DDL에서 제외한다.")
  public void removeTransientColumns() {
    List<MetaDataColumn> dataColumnList = new ArrayList<>();
    for (Field declaredField : personWithTransient.getDeclaredFields()){
      Type type = h2Dialect.convertType(declaredField);
      dataColumnList.add(MetaDataColumn.of(declaredField, type));
    }
    MetaDataColumns dataColumns = MetaDataColumns.of(dataColumnList);

    assertThat(dataColumnList.getColumnClause()).isEqualTo("");
  }
}
