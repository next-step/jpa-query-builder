package persistence.sql.ddl.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DDLColumn;
import persistence.sql.ddl.Person;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("DDL Column 테스트")
class DDLColumnsTest {
    
    @Test
    void Entity_컬럼_이용한_DDL_가져오기() {
        DDLColumn ddlColumn = new DDLColumn(Person.class.getDeclaredFields());
        String sql = ddlColumn.makeColumnsDDL();
        assertThat(sql).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255) NULL,age INTEGER NULL,email VARCHAR(255) NOT NULL");
    }

    @Test
    void 객체_생성시_매개변수_null_이거나_비어있을_경우() {
        assertAll(() -> {
            assertThatThrownBy(() -> new DDLColumn(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("필드가 존재하지 않습니다.");
            assertThatThrownBy(() -> new DDLColumn(new Field[0]))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("필드가 존재하지 않습니다.");
        });
    }

}
