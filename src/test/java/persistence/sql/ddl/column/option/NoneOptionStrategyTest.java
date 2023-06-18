package persistence.sql.ddl.column.option;

import fixture.PersonV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class NoneOptionStrategyTest {
    private final OptionStrategy optionStrategy = NoneOptionStrategy.INSTANCE;

    @Test
    @DisplayName("NoneOptionStrategy 는 빈 옵션 값을 반환한다")
    public void noneOption() {
        Field declaredField = PersonV1.class.getDeclaredFields()[0];
        Field declaredField2 = PersonV1.class.getDeclaredFields()[1];

        assertAll(
                () -> {
                    assertThat(optionStrategy.options(declaredField)).isEqualTo("");
                },
                () -> assertThat(optionStrategy.options(declaredField2)).isEqualTo("")
        );
    }
}