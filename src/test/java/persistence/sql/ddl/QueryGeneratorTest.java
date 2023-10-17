package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueryGeneratorTest {

	@Test
	@DisplayName("create 쿼리를 반환한다.")
	void createQuery() {
		QueryGenerator queryGenerator = new QueryGenerator();
		String result = queryGenerator.getCreateQuery();

		String expected = "CREATE TABLE users (\n"
			+ "id BIGINT AUTO_INCREMENT PRIMARY KEY,\n"
			+ "nick_name VARCHAR(255),\n"
			+ "old INT,\n"
			+ "email VARCHAR(255) NOT NULL\n"
			+ ");";
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("drop 쿼리를 반환한다.")
	void dropQuery() {
		QueryGenerator queryGenerator = new QueryGenerator();
		String result = queryGenerator.getDropQuery();

		String expected = "DROP TABLE users;";

		assertThat(result).isEqualTo(expected);
	}
}
