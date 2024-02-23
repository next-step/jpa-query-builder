package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.Column;
import persistence.sql.model.Sql;
import persistence.sql.model.Table;

import java.util.List;

public class DDLQueryBuilder {

    private final Dialect dialect;

    public DDLQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildCreateQuery(Table table) {
        String tableName = table.getName();
        List<Column> tableColumns = table.getColumns();

        return new Sql.Builder(dialect)
                .create()
                .and()
                .table(tableName)
                .and()
                .leftParenthesis()
                .columns(tableColumns)
                .rightParenthesis()
                .build();
    }

    public String buildDropQuery(Table table) {
        String tableName = table.getName();

        return new Sql.Builder(dialect)
                .drop()
                .and()
                .table(tableName)
                .build();
    }
}
