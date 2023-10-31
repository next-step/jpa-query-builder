package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.MetaData;

public class DropQueryBuilder extends QueryBuilder {

    public DropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query queryForObject(MetaData metaData, StringBuilder sb) {
        StringBuilder query = sb.append("drop table ")
                .append(metaData.getEntity());
        return new Query(query);
    }
}
