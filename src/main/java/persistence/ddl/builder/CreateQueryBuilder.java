package persistence.ddl.builder;

import persistence.meta.MetaData;


//CREATE TABLE IF NOT EXISTS members (
//    m_num bigint(5) NOT NULL AUTO_INCREMENT,
//    name varchar(100) NOT NULL,
//    age int(3) NOT NULL,
//    PRIMARY KEY (m_num)
//    );

public class CreateQueryBuilder {
  private static final String CREATE_SQL_QUERY = "CREATE TABLE %s;";

  public CreateQueryBuilder() {
  }

  public String createCreateQuery(MetaData metaData){
    return String.format(CREATE_SQL_QUERY, metaData.getCreateClause());
  }

}


// meta data
//
// meta data를 obj 로 받아서 해당 obj를 가지고
// 진행하면된다.
