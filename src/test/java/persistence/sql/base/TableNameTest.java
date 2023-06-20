package persistence.sql.base;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableNameTest {

    @Test
    @DisplayName("테이블 이름을 문자열로 반환한다")
    void tableName() {
        // given
        Class<PersonV3> personV3Class = PersonV3.class;
        TableName tableName = new TableName(personV3Class);

        // when
        String name = tableName.getName();
        // then
        assertThat(name).isEqualTo("users");
    }
}