package persistence.sql.ddl;

import persistence.dialect.Dialect;
import sources.MetaData;

import java.util.Map;

public class QueryBuilder {

    Dialect dialect;
    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public void create(MetaData metaData, StringBuilder sb) {
        sb.append("create table ")
                .append(metaData.getEntity())
                .append(" ")
                .append(metaData.getId())
                .append(" int not null auto_increment, ");

    }

    private StringBuilder columnTypeName(StringBuilder sb, Map<String, String> columns) {
        columns.forEach((s, s2) -> sb.append(" ").append(s2).append(" ").append(dialect.getPrimitiveTypeName(s1)));

    }
}
