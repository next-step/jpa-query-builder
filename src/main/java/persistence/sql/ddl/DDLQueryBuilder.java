package persistence.sql.ddl;

import persistence.sql.model.Sql;
import persistence.sql.dialect.Dialect;
import persistence.sql.model.Column;
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
                .table(tableName)
                .leftParenthesis()
                .columns(columns)
                .rightParenthesis()
                .build();
    }
}
