package persistence.sql.dml.builder;

public class SelectQueryBuilder {
  private static final String SELECT_SQL_QUERY = "SELECT %s FROM %s;";
  private static final String SELECT_WHERE_SQL_QUERY = "SELECT %s FROM %s WHERE %s=%s;";


  public String createSelectQuery(String columnClause, String tableName) {

    return String.format(SELECT_SQL_QUERY, columnClause, tableName);
  }

  public String createSelectByFieldQuery(String columnClause, String tableName, String targetName, Object targetValue) {

    return String.format(SELECT_WHERE_SQL_QUERY, columnClause, tableName, targetName, targetValue);
  }


}
