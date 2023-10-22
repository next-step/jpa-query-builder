package persistence.sql.meta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("1.2 MetaDataTable을 Clazz에서 추출합니다.")
public class MetaDataTableTest {

  @Test
  @DisplayName("1.2.1 Clazz의 클래스 명과 table의 명이 매칭 됩니다.")
  public void matchClazzNameandTableName() {

  }

  @Test
  @DisplayName("1.2.1 애노테이션이 누락 되었을 때, metaDataTable을 생성 할 수 없다.")
  public void requireEntityAnnotation() {

  }

  @Test
  @DisplayName("1.2.3 Table 애노테이션에 name 값을 추가하였을 때, 해당 이름으로 DDL을 생성한다.")
  public void createTableWithAnnotationName() {

  }
}
