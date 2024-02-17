package persistence.study;

import org.junit.jupiter.api.Test;

public class StringTest {
    class Sut {
        public String test() {
            return "123";
        }
    }
    @Test
    void convert() {
        // todo: 테스트 코드 만들어보기
        assert(new Sut().test().equals("123"));
    }

}
