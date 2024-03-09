package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.Person;

class ColumnValueTranslatorTest {

    private final ColumnValueTranslator columnValueTranslator = new ColumnValueTranslator();

    @DisplayName("엔티티 객체를 통해서 정상적으로 Column Value Clause 쿼리를 가져올 수 있다.")
    @Test
    void getColumnValueClause() {
        // given
        Person person = new Person("nick", 20, "nick@gmail.com");

        // when
        String columnClause = columnValueTranslator.getColumnValueClause(person);

        // then
        assertThat(columnClause).isEqualTo("'nick', 20, 'nick@gmail.com'");
    }
}
