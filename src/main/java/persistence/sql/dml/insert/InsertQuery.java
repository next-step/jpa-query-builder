package persistence.sql.dml.insert;

import java.util.List;
import persistence.sql.dml.ColumnClause;
import persistence.sql.dml.ValueClause;
import persistence.sql.usecase.GetTableNameFromClassUseCase;
import persistence.sql.vo.TableName;

public class InsertQuery {
    private final GetTableNameFromClassUseCase getTableNameFromClassUseCase;
    private final List<ColumnClause> columnClauses;
    private final List<ValueClause> valueClauses;

    public InsertQuery(GetTableNameFromClassUseCase getTableNameFromClassUseCase, List<ColumnClause> columnClauses, List<ValueClause> valueClauses) {
        this.getTableNameFromClassUseCase = getTableNameFromClassUseCase;
        this.columnClauses = columnClauses;
        this.valueClauses = valueClauses;
    }

    public TableName getTableName(Class<?> cls) {
        return getTableNameFromClassUseCase.execute(cls);
    }

}
