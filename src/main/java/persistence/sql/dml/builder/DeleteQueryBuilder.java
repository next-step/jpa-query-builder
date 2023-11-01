package persistence.sql.dml.builder;


public class DeleteQueryBuilder {
  private static final String DELETE_SQL_QUERY = "DELETE FROM %s WHERE %s = %s;";

  public String createDeleteQuery(String tableName, String targetName, Object targetValue) {

    return String.format(DELETE_SQL_QUERY, tableName, targetName, targetValue);
  }
}
