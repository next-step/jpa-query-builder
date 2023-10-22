package persistence.sql.ddl.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("1.2 Entity를 CREATE DLL로 변환합니다.")
public class CreateQueryBuilderTest {

  @Test
  @DisplayName("1.2.1.1 @Entity, @id가 표기된 class의 TABLE CREATE DDL을 생성합니다.")
  public void createDdlFromEntityClass() {

  }

  @Test
  @DisplayName("1.2.2.1 @GeneratedValue와 @Column의 표기된 정보를 이용해 TABLE CREATE DDL을 생성합니다.")
  public void createDdlFromPkAndColumnAnnotations() {

  }

  @Test
  @DisplayName("1.2.2.2 @GeneratedValue의 PK 전략과 @Column의 Not Null, name에 맞게 DDL을 생성합니다.")
  public void createDdlFromGeneratedValueAndColumnAnnotations() {

  }

  @Test
  @DisplayName("1.2.3.1 @Table과 @Transient에 표기된 정보를 이용해 TABLE CREATE DDL을 생성합니다.")
  public void createDdlFromTableAndTransientAnnotations() {

  }

}
