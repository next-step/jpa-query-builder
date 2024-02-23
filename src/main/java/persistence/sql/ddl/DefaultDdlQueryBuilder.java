package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.mapping.Column;
import persistence.sql.mapping.Table;

import java.util.stream.Collectors;

import static persistence.sql.QueryBuilderConst.ENTER;
import static persistence.sql.QueryBuilderConst.SPACE;

public class DefaultDdlQueryBuilder implements DdlQueryBuilder {

    private final Dialect dialect;

    public DefaultDdlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String buildCreateQuery(final Table table) {
        final StringBuilder statement = new StringBuilder()
                .append("create table ")
                .append(table.getName())
                .append(" (")
                .append(ENTER)
                .append(SPACE);

        final String columnsQuery = table.getColumns()
                .stream()
                .map(this::buildColumnQuery)
                .collect(Collectors.joining("," + ENTER + SPACE));

        statement.append(columnsQuery);

        if (table.hasPrimaryKey()) {
            statement.append(",")
                    .append(ENTER)
                    .append(SPACE)
                    .append(table.getPrimaryKey().sqlConstraintString());
        }

        return statement.append(ENTER).append(")").toString();
    }

    private String buildColumnQuery(final Column column) {
        final String columnType = column.getSqlType(dialect);

        final StringBuilder columnQuery = new StringBuilder()
                .append(column.getName())
                .append(" ")
                .append(columnType);

        if (column.isIdentifierKey()) {
            columnQuery.append(" ")
                    .append(dialect.getIdentityColumnSupport().getIdentityColumnString());
        } else {
            if (column.isNotNull()) {
                columnQuery.append(" not null");
            }
        }

        return columnQuery.toString();
    }

    @Override
    public String buildDropQuery(final Table table) {
        return "drop table if exists " +
                table.getName() +
                ";";
    }

}
