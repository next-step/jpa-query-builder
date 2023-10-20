package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionUtilsTest {

    @Nested
    @DisplayName("카멜케이스 형식으로 작성된 문자열을 List 형식으로 반환하는 Utils method 테스트")
    class getWordsFromCamelCase {
        @Test
        @DisplayName("별도 조건이 필요없는 findByAll의 경우 빈 값을 반환")
        void success() {
            //given
            final String camelCase = "findByAll";

            //when
            List<String> result = ConditionUtils.getWordsFromCamelCase(camelCase);

            //then
            assertThat(result).size().isZero();
        }

        @Test
        @DisplayName("findByIdAndName의 경우 Id와 Name을 반환한다")
        void getFindByIdAndName() {
            //given
            final String methodName = "findByIdAndName";

            //when
            List<String> result = ConditionUtils.getWordsFromCamelCase(methodName);

            //then
            assertThat(result).containsExactly("id", "name");
        }

        @Test
        @Disabled("기능 미구현")
        @DisplayName("findByIdAndMethodName의 경우 Id와 methodName을 반환한다")
        void getFindByIdAndMethodName() {
            //given
            final String methodName = "findByIdAndMethodName";

            //when
            List<String> result = ConditionUtils.getWordsFromCamelCase(methodName);

            //then
            assertThat(result).containsExactly("id", "methodName");
        }
    }
}
