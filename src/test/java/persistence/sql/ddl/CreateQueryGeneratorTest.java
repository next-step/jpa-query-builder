package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateQueryGeneratorTest {

	@Test
	@DisplayName("create 쿼리를 반환한다.")
	void createQuery() {
		CreateQueryGenerator createQueryGenerator = new CreateQueryGenerator(Person.class);
		String result = createQueryGenerator.getCreateQuery();

		String expected = "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR(255),old INT,email VARCHAR(255) NOT NULL);";
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("drop 쿼리를 반환한다.")
	void dropQuery() {
		CreateQueryGenerator createQueryGenerator = new CreateQueryGenerator(Person.class);
		String result = createQueryGenerator.getDropQuery();

		String expected = "DROP TABLE users;";

		assertThat(result).isEqualTo(expected);
	}
}
