package persistence.sql.model;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.ExceptionUtil;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EntityColumnNamesTest {

    @Test
    void 엔티티_컬럼_이름_조회() {
        EntityColumnNames entityColumnNames = new EntityColumnNames(Person.class);
        String columnNames = entityColumnNames.getColumnNames();

        assertThat(columnNames).isEqualTo("nick_name, old, email");
    }

    @Test
    void 엔티티_컬럼_Class_NULL로_생성_실패() {
        assertThatThrownBy(() -> new EntityColumnNames(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionUtil.CLASS_NULL_MESSAGE);
    }


}
