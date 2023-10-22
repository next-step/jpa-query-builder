package persistence.sql.meta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("1.2 MetaDataColumn을 Clazz에서 추출합니다.")
public class MetaDataColumnTest {

  @Test
  @DisplayName("1.2.1 @Id 애노테이션으로 Column 을 생성한다.")
  public void createWithIdAnnotation() {

  }
  @Test
  @DisplayName("1.2.1 @Column 애노테이션 없이 Column 을 생성한다.")
  public void createWithoutColumnAnnotation() {

  }

  @Test
  @DisplayName("1.2.2 @Column 애노테이션의 name 값으로 Column 을 생성한다.")
  public void createWithNameValueinColumnAnnotation() {

  }

  @Test
  @DisplayName("1.2.2 @Column 애노테이션의 nullable 값으로 Column 을 생성한다.")
  public void createWithNullableColumnAnnotation() {

  }


  @Test
  @DisplayName("1.2.2 @GeneratedValue 애노테이션의 값으로 Column 을 생성한다.")
  public void createWithGeneratedValueAnnotation() {

  }
}
