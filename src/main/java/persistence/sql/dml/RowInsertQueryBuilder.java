package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.SQLEscaper;
import persistence.sql.TableSQLMapper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RowInsertQueryBuilder extends QueryBuilder {
    public RowInsertQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) {

        return "INSERT INTO " +
            TableSQLMapper.getTableName(object.getClass()) +
            " " +
            columnsClause(object) +
            " VALUES " +
            valueClause(object) +
            ";";
    }

    private String columnsClause(Object object) {
        return "(" +
            Arrays
                .stream(TableSQLMapper.getValidFields(object))
                .map(TableSQLMapper::getColumnName)
                .map(SQLEscaper::escapeNameByBacktick)
                .collect(Collectors.joining(", ")) +
            ")";
    }

    private String valueClause(Object object) {
        return "(" +
            Arrays
                .stream(TableSQLMapper.getValidFields(object))
                .map(field -> TableSQLMapper.getValueOfColumn(field, object))
                .map(TableSQLMapper::changeColumnValueToString)
                .collect(Collectors.joining(", ")) +
            ")";
    }
}
