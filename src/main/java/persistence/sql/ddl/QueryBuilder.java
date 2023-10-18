package persistence.sql.ddl;

import persistence.dialect.Dialect;
import sources.MetaData;

import java.util.Map;

public class QueryBuilder {

    Dialect dialect;
    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public StringBuilder create(MetaData metaData, StringBuilder sb) {
        return sb.append("create table ")
                .append(metaData.getEntity())
                .append(" (")
                .append(metaData.getId())
                .append(" int not null auto_increment, ")
                .append(columnTypeName(metaData.getColumns()))
                .append("primary key(")
                .append(metaData.getId())
                .append("))")
        ;

    }

    private StringBuilder columnTypeName(Map<String, String> columns) {
        StringBuilder sb = new StringBuilder();
        columns.forEach((type, name) -> sb.append(" ")
                .append(name)
                .append(" ")
                .append(dialect.transferType(type))
                .append(", "));
        return sb;
    }
}
