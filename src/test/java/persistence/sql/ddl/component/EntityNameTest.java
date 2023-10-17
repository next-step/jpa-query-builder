package persistence.sql.ddl.component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EntityNameTest {

    @DisplayName("클래스의 테이블 이름을 추출한다.")
    @ParameterizedTest
    @MethodSource("getTableNameTestParams")
    void getTableNameTest(Class<?> testClass, String expected) {
        assertThat(EntityName.getTableName(testClass)).isEqualTo(expected);
    }

    private static Stream<Arguments> getTableNameTestParams() {
        return Stream.of(
                Arguments.of(TestClass1.class, "testclass1"),
                Arguments.of(TestClass2.class, "testclass2"),
                Arguments.of(TestClass3.class, "test_class")
        );
    }

    @Entity
    private static class TestClass1 {
        @Id
        private Long id;
    }

    @Table
    @Entity
    private static class TestClass2 {
        @Id
        private Long id;
    }

    @Table(name = "test_class")
    @Entity
    private static class TestClass3 {
        @Id
        private Long id;
    }

}
