package persistence.sql.dml.insert;

import java.util.List;
import persistence.sql.dml.ColumnClause;
import persistence.sql.dml.ValueClause;
import persistence.sql.vo.TableName;

public class InsertQuery {
    private final TableName tableName;
    private final List<ColumnClause> columnClauses;
    private final List<ValueClause> valueClauses;

    public InsertQuery(TableName tableName, List<ColumnClause> columnClauses, List<ValueClause> valueClauses) {
        this.tableName = tableName;
        this.columnClauses = columnClauses;
        this.valueClauses = valueClauses;
    }

    public TableName getTableName() {
        return tableName;
    }

    public List<ColumnClause> getColumnClauses() {
        return columnClauses;
    }

    public List<ValueClause> getValueClauses() {
        return valueClauses;
    }
}
