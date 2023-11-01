package persistence.sql.dml.builder;

public class InsertQueryBuilder {
  private static final String INSERT_SQL_QUERY = "INSERT INTO %s (%s) values (%s);";

  public String createInsertQuery(String talbeName, String valueClause, String columnClause) {
    return String.format(INSERT_SQL_QUERY, talbeName, valueClause, columnClause);
  }


}
