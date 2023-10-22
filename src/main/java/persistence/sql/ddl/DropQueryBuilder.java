package persistence.sql.ddl;

import persistence.dialect.Dialect;
import sources.MetaData;

public class DropQueryBuilder extends QueryBuilder{

    public DropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public StringBuilder drop(MetaData metaData, StringBuilder sb) {
        return sb.append("drop table ")
                .append(metaData.getEntity());
    }
}
