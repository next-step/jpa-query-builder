package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.Column;
import persistence.sql.model.Sql;
import util.EntityAnalyzer;

import java.util.List;

public class DDLQueryBuilder {

    private final Dialect dialect;

    public DDLQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildCreateQuery(Class<?> clazz) {
        String tableName = EntityAnalyzer.getTableName(clazz);
        List<Column> columns = EntityAnalyzer.getColumns(clazz);
        return new Sql.Builder(dialect)
                .create()
                .and()
                .table(tableName)
                .and()
                .leftParenthesis()
                .columns(columns)
                .rightParenthesis()
                .build();
    }

    public String buildDropQuery(Class<?> clazz) {
        String tableName = EntityAnalyzer.getTableName(clazz);
        return new Sql.Builder(dialect)
                .drop()
                .and()
                .table(tableName)
                .build();
    }
}
