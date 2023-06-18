package persistence.sql.ddl.column.option;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnOptionStrategyTest {
    private final OptionStrategy optionStrategy = new ColumnOptionStrategy();

    @Test
    @DisplayName("@Column 어노테이션이 있을 경우에 true 를 반환합니다")
    public void supports() throws NoSuchFieldException {
        Field id = PersonV3.class.getDeclaredField("id");
        Field name = PersonV3.class.getDeclaredField("name");

        assertAll(
                () -> assertThat(optionStrategy.supports(id)).isFalse(),
                () -> assertThat(optionStrategy.supports(name)).isTrue()
        );
    }

    @Test
    @DisplayName("@Column 옵션에 대한 값들을 생성합니다")
    public void idOption() throws NoSuchFieldException {
        Field email = PersonV3.class.getDeclaredField("email");

        String options = optionStrategy.options(email);

        assertThat(options).isEqualTo("not null");
    }
}