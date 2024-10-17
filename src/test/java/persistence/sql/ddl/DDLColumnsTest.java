package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Dialect;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.DDLColumn;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("DDL Column 테스트")
class DDLColumnsTest {

    @Test
    void Entity_컬럼_이용한_DDL_가져오기() {
        Dialect h2Dialect = new H2Dialect();
        DDLColumn ddlColumn = new DDLColumn(Person.class.getDeclaredFields(), h2Dialect);
        String sql = ddlColumn.makeColumnsDDL();
        assertThat(sql).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT,nick_name VARCHAR(255) NULL,old INTEGER NULL,email VARCHAR(255) NOT NULL");
    }

    @Test
    void 객체_생성시_매개변수_null_이거나_비어있을_경우() {
        Dialect dialect = new H2Dialect();
        assertAll(() -> {
            assertThatThrownBy(() -> new DDLColumn(null, dialect))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("필드가 존재하지 않습니다.");
            assertThatThrownBy(() -> new DDLColumn(new Field[0], dialect))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("필드가 존재하지 않습니다.");
        });
    }

}
