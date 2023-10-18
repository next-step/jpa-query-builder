package persistence.sql.ddl;

import sources.MetaData;

public class QueryBuilder {
    public QueryBuilder() {

    }

    public void create(MetaData metaData, StringBuilder sb) {
        sb.append("create table ").append(metaData.getEntity()).append(" ").append(metaData.getId()).append(" int not null auto_increment, ");

    }
}
