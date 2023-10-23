package persistence.sql.ddl;

import persistence.dialect.Dialect;
import sources.MetaData;

public class DropQueryBuilder extends QueryBuilder{

    private Query query;

    public DropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query drop(MetaData metaData, StringBuilder sb) {
        StringBuilder query = sb.append("drop table ")
                .append(metaData.getEntity());
        return new Query(query);
    }
}
