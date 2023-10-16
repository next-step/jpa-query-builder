package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnTypeTest {

    @Test
    @DisplayName("존재하지 않는 타입")
    void typeIsNotExists() {
        assertThatThrownBy(() -> ColumnType.getSqlType(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타입이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("타입변환 성공")
    void getSqlType() {
        String sqlType = ColumnType.getSqlType(Integer.class);
        assertThat(sqlType).isEqualTo("INT");
    }

}