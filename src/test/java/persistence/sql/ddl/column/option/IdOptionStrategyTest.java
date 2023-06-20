package persistence.sql.ddl.column.option;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class IdOptionStrategyTest {
    private final OptionStrategy optionStrategy = new IdOptionStrategy(new H2GeneratedValueStrategy());

    @Test
    @DisplayName("@Id 어노테이션이 있을 경우에 true 를 반환합니다")
    public void supports() throws NoSuchFieldException {
        Field id = PersonV3.class.getDeclaredField("id");
        Field name = PersonV3.class.getDeclaredField("name");

        assertAll(
                () -> assertThat(optionStrategy.supports(id)).isTrue(),
                () -> assertThat(optionStrategy.supports(name)).isFalse()
        );
    }

    @Test
    @DisplayName("@Id 옵션에 대한 값들을 생성합니다")
    public void idOption() throws NoSuchFieldException {
        Field id = PersonV3.class.getDeclaredField("id");

        String options = optionStrategy.options(id);

        assertThat(options).isEqualTo("primary key auto_increment");
    }
}