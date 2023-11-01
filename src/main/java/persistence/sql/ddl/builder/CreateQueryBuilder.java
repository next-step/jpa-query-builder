package persistence.sql.ddl.builder;

public class CreateQueryBuilder {
  private static final String CREATE_SQL_QUERY = "CREATE TABLE %s;";

  public CreateQueryBuilder() {
  }

  public String createCreateQuery(String createClause) {
    return String.format(CREATE_SQL_QUERY, createClause);
  }

}
