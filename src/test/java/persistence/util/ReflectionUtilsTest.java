package persistence.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.PersistenceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ReflectionUtilsTest {

    static class Fixture {
        private final String testString = "test";
        private final Integer testInteger = 1;
        private final Long testLong = 2L;
    }

    private Fixture fixture;
    @BeforeEach
    void setUp() {
        fixture = new Fixture();
    }

    @Test
    @DisplayName("getFieldValues 로 object 의 필드들을 리스트로 반환 받을 수 있다.")
    void getFieldValuesTest() {
        final List<Object> result = ReflectionUtils.getFieldValues(fixture, List.of("testString", "testInteger", "testLong"));

        assertThat(result).containsExactly("test", 1, 2L);
    }

    @Test
    @DisplayName("getFieldValue 로 object 의 필드를 반환 받을 수 있다.")
    void getFieldValueTest() {
        final Object stringResult = ReflectionUtils.getFieldValue(fixture, "testString");
        final Object integerResult = ReflectionUtils.getFieldValue(fixture, "testInteger");
        final Object longResult = ReflectionUtils.getFieldValue(fixture, "testLong");

        assertThat(stringResult).isEqualTo("test");
        assertThat(integerResult).isEqualTo(1);
        assertThat(longResult).isEqualTo(2L);
    }

    @Test
    @DisplayName("getFieldValue 로 object 에 fieldName 의 필드변수가 없으면 Exception 을 던진다.")
    void getFieldValueFailureTest() {
        assertThatThrownBy(()->ReflectionUtils.getFieldValue(fixture, "none"))
                .isInstanceOf(PersistenceException.class);
    }
}
