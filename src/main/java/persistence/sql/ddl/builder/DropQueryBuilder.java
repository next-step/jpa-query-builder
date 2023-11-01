package persistence.sql.ddl.builder;

public class DropQueryBuilder {
  private static final String DROP_SQL_QUERY = "DROP TABLE %s;";

  public DropQueryBuilder() {
  }

  public String createDropQuery(String dropClause) {
    return String.format(DROP_SQL_QUERY, dropClause);
  }
}

