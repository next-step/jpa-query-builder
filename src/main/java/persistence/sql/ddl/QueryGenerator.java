package persistence.sql.ddl;

public class QueryGenerator {

	public String createQuery() {
		return "CREATE TABLE person (id BIGINT NOT NULL, name VARCHAR(255), age INTEGER, PRIMARY KEY (id))";
	}
}
