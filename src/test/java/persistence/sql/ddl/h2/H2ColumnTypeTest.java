package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.h2.H2ColumnDialect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2ColumnTypeTest {

    @Test
    @DisplayName("H2 존재하지 않는 타입")
    void typeIsNotExists() {
        assertThatThrownBy(() -> H2ColumnDialect.getSqlType(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타입이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("H2 타입변환 성공")
    void getSqlType() {
        String sqlType = H2ColumnDialect.getSqlType(Integer.class);
        assertThat(sqlType).isEqualTo("INT");
    }
}
