package persistence.sql.ddl;

import persistence.dialect.Dialect;
import sources.ColumnMetaData;
import sources.MetaData;

import java.util.List;

public class CreateQueryBuilder extends QueryBuilder{

    private Query query;

    public CreateQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query create(MetaData metaData, StringBuilder sb) {
        StringBuilder query = sb.append("create table ")
                .append(metaData.getEntity())
                .append(" (")
                .append(metaData.getId())
                .append(" ")
                .append(columnTypeName(metaData.getColumns()))
                .append(" )");
        return new Query(query);
    }

    private String columnTypeName(List<ColumnMetaData> columns) {
        StringBuilder columnQuery = new StringBuilder();
        for (ColumnMetaData column : columns) {
            columnQuery.append(columnQueryBuilder(column));
        }
        return columnQuery.toString();
    }

    private String columnQueryBuilder(ColumnMetaData columnMetaData) {
        String query = ", "+ columnMetaData.getName() + " "+ dialect.javaTypeToJdbcType(columnMetaData.getType());

        if(columnMetaData.getType().equals("String")) {
            query = query + "(" +columnMetaData.getLength()+") ";
        }

        if(!columnMetaData.isNullable()) {
            query = query + " not null";
        }
        return query;
    }
}
