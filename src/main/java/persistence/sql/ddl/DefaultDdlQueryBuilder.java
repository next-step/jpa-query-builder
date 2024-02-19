package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.query.Column;
import persistence.sql.query.Query;

import java.util.stream.Collectors;

public class DefaultDdlQueryBuilder implements DdlQueryBuilder {

    private final Dialect dialect;

    public DefaultDdlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String buildCreateQuery(final Query query) {
        final StringBuilder ddl = new StringBuilder()
                .append("CREATE TABLE ")
                .append(query.getTableName())
                .append(" (\n")
                .append(SPACE);

        final String columnsQuery = query.getColumns()
                .stream()
                .map(this::buildColumnQuery)
                .collect(Collectors.joining(",\n" + SPACE));

        ddl.append(columnsQuery)
                .append("\n")
                .append(");");

        return ddl.toString();
    }

    private String buildColumnQuery(final Column column) {
        final String columnType = dialect.convertColumnType(column.getType(), column.getLength());

        final StringBuilder columnQuery = new StringBuilder()
                .append(column.getName())
                .append(" ")
                .append(columnType);

        final String keywords = dialect.toDialectKeywords(column);

        if (!keywords.isBlank()) columnQuery.append(" ")
                .append(keywords);


        return columnQuery.toString();
    }

    @Override
    public String buildDropQuery(final Query query) {
        return "DROP TABLE IF EXISTS " +
                query.getTableName() +
                ";";
    }

}
