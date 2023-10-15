package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.JDBCType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityFiledTest {
    @Test
    @DisplayName("문자열 필드에 대한 문자열 DDL을 만들어준다")
    void varcharGenerateFiledDDL() {
        EntityFiled entityFiled = new EntityFiled("id", JDBCType.VARCHAR);

        assertThat(entityFiled.generateFiledDDL()).isEqualTo("id varchar(255)");
    }

    @Test
    @DisplayName("필드에 대한 정수형 DDL을 만들어준다")
    void generateFiledDDL() {
        EntityFiled entityFiled = new EntityFiled("id", JDBCType.INTEGER);

        assertThat(entityFiled.generateFiledDDL()).isEqualTo("id integer");
    }


}
