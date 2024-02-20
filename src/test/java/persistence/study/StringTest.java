package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {
	@Test
	void convert() {
		// todo: 테스트 코드 만들어보기
		int intValue = 123;
		String strValue = Integer.toString(intValue);
		assertThat(strValue.equals("123")).isTrue();
	}
}
