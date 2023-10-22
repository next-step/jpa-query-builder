package persistence.sql.dialect.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class H2TypeDialectTest {

    private static final H2TypeDialect h2TypeDialect = H2TypeDialect.getInstance();

    @Test
    @DisplayName("H2 존재하지 않는 타입")
    void typeIsNotExists() {
        assertThatThrownBy(() -> h2TypeDialect.getSqlType(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타입이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("H2 타입변환 성공")
    void getSqlType() {
        String sqlType = h2TypeDialect.getSqlType(Integer.class);
        assertThat(sqlType).isEqualTo("INT");
    }

}
