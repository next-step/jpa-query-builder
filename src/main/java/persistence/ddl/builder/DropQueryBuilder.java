package persistence.ddl.builder;

import persistence.meta.MetaData;

public class DropQueryBuilder {
  private static final String DROP_SQL_QUERY = "DROP TABLE %s;";

  public DropQueryBuilder() {
  }

  public String createDropQuery(MetaData metaData){
    return String.format(DROP_SQL_QUERY, metaData.getDropClause());
  }
}
