package persistence.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    void convert() {
        // todo: 테스트 코드 만들어보기
        Integer numStr = 123;

        String strNum = String.valueOf(numStr);

        Assertions.assertThat("123").isEqualTo(strNum);
    }
}
