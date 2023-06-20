package persistence.sql.base;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnNamesTest {

    @Test
    @DisplayName("컬럼들의 컬럼 이름을 문자열로 반환한다")
    void names() throws NoSuchFieldException {
        // given
        Class<PersonV3> personV3Class = PersonV3.class;
        ColumnNames columnNames = new ColumnNames(List.of(
                new ColumnName(personV3Class.getDeclaredField("id")),
                new ColumnName(personV3Class.getDeclaredField("name"))
        ));

        // when
        String names = columnNames.names();

        // then
        assertThat(names).isEqualTo("id, nick_name");
    }
}