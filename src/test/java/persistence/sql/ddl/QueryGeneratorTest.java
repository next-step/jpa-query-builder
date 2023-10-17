package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueryGeneratorTest {

	@Test
	@DisplayName("person create 쿼리를 반환한다.")
	void personCreateQuery() {
		QueryGenerator queryGenerator = new QueryGenerator();
		String result = queryGenerator.createQuery();

		assertThat(result).isEqualTo("CREATE TABLE person (id BIGINT NOT NULL, name VARCHAR(255), age INTEGER, PRIMARY KEY (id))");
	}
}
