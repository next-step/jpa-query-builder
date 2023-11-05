package persistence.sql.dml.insert;

import java.util.LinkedHashMap;
import java.util.Map;
import persistence.sql.dml.ColumnClause;
import persistence.sql.dml.ValueClause;
import persistence.sql.vo.TableName;

public class InsertQuery {
    private final TableName tableName;
    private final Map<ColumnClause, ValueClause> columToValueMap = new LinkedHashMap<>();

    public InsertQuery(TableName tableName) {
        this.tableName = tableName;
    }

    public TableName getTableName() {
        return tableName;
    }

    public void addFieldValue(ColumnClause columnClause, ValueClause valueClause) {
        this.columToValueMap.put(columnClause, valueClause);
    }

    public Map<ColumnClause, ValueClause> getColumToValueMap() {
        return columToValueMap;
    }
}
