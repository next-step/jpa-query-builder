package persistence.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.exception.PersistenceException;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ReflectionUtilsTest {

    static class Fixture {
        private String testString = "test";
        private Integer testInteger = 1;
        private Long testLong = 2L;
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

    @Test
    @DisplayName("createInstance 로 Class 로부터 object 를 생성 할 수 있다.")
    void createInstanceTest() {
        final Fixture instance = ReflectionUtils.createInstance(Fixture.class);

        assertThat(instance).isNotNull();
    }

    @Test
    @DisplayName("injectField 로 instance 에 값을 주입 할 수 있다.")
    void injectFieldTest() {
        ReflectionUtils.injectField(fixture, "testString", "1");
        ReflectionUtils.injectField(fixture, "testInteger", 2);
        ReflectionUtils.injectField(fixture, "testLong", 3L);

        assertThat(fixture.testString).isEqualTo("1");
        assertThat(fixture.testInteger).isEqualTo(2);
        assertThat(fixture.testLong).isEqualTo(3L);
    }

    @Test
    @DisplayName("instance 에 해당 fieldName 의 필드변수가 없으면 Exception 을 던진다.")
    void noFieldNameTest() {
        assertThatThrownBy(()->ReflectionUtils.injectField(fixture, "none", 1))
                .isInstanceOf(PersistenceException.class);
    }

    @ParameterizedTest(name = "필드명 : {0} - value : {1}")
    @MethodSource("wrongArgumentProvider")
    @DisplayName("injectField 로 object field 에 맞지않는 타입을 주입하면 Exception 을 던진다..")
    void classTypeNotMatchedTest(final String fieldName, final Object value) {
        assertThatThrownBy(()->ReflectionUtils.injectField(fixture, fieldName, value))
                .isInstanceOf(PersistenceException.class);
    }

    private static Stream<Arguments> wrongArgumentProvider() {
        return Stream.of(
                Arguments.of("testString", 1)
                ,Arguments.of("testInteger", "test")
                ,Arguments.of("testLong", "test")
        );
    }
}
