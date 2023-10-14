package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StringTest {

	@Test
	void convert() {
		int number = 123;

		String result = String.valueOf(number);

		assertThat(result).isEqualTo("123");
	}
}
