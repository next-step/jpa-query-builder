package persistence.sql.ddl.builder;

import persistence.meta.MetaData;

public class CreateQueryBuilder {
  private static final String CREATE_SQL_QUERY = "CREATE TABLE %s;";

  public CreateQueryBuilder() {
  }

  public String createCreateQuery(MetaData metaData) {
    return String.format(CREATE_SQL_QUERY, metaData.getCreateClause());
  }

}
