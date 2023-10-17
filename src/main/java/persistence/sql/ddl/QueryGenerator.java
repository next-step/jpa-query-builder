package persistence.sql.ddl;

public class QueryGenerator {

	public String createQuery() {
		return "CREATE TABLE users (\n"
			+ "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n"
			+ "    nick_name VARCHAR(255),\n"
			+ "    old INT,\n"
			+ "    email VARCHAR(255) NOT NULL\n"
			+ ");";
	}
}
