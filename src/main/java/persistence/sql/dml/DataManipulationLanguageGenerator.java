package persistence.sql.dml;

import jakarta.persistence.GenerationType;
import persistence.sql.dml.delete.DeleteQuery;
import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.dml.select.SelectQuery;
import persistence.sql.dml.where.ConditionType;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.usecase.GetFieldFromClassUseCase;
import persistence.sql.usecase.GetFieldValueUseCase;
import persistence.sql.usecase.GetTableNameFromClassUseCase;
import persistence.sql.vo.DatabaseField;
import persistence.sql.vo.DatabaseFields;
import persistence.sql.vo.TableName;
import persistence.sql.vo.type.BigInt;

public class DataManipulationLanguageGenerator {
    private final GetTableNameFromClassUseCase getTableNameFromClassUseCase;
    private final GetFieldFromClassUseCase getFieldFromClassUseCase;
    private final GetFieldValueUseCase getFieldValueUseCase;

    public DataManipulationLanguageGenerator(GetTableNameFromClassUseCase getTableNameFromClassUseCase, GetFieldFromClassUseCase getFieldFromClassUseCase, GetFieldValueUseCase getFieldValueUseCase) {
        this.getTableNameFromClassUseCase = getTableNameFromClassUseCase;
        this.getFieldFromClassUseCase = getFieldFromClassUseCase;
        this.getFieldValueUseCase = getFieldValueUseCase;
    }

    public InsertQuery buildInsertQuery(Object object) {
        TableName tableName = getTableNameFromClassUseCase.execute(object.getClass());
        DatabaseFields databaseFields = getFieldFromClassUseCase.execute(object.getClass());
        InsertQuery insertQuery = new InsertQuery(tableName);
        for(DatabaseField databaseField : databaseFields.getDatabaseFields()) {
            if(databaseField.isPrimary() && databaseField.getPrimaryKeyGenerationType() == GenerationType.IDENTITY) {
                continue;
            }
            insertQuery.addFieldValue(new ColumnClause(databaseField.getDatabaseFieldName()), new ValueClause(getFieldValueUseCase.execute(object, databaseField), databaseField.getDatabaseType()));
        }
        return insertQuery;
    }

    public SelectQuery buildSelectQuery(Class<?> cls) {
        TableName tableName = getTableNameFromClassUseCase.execute(cls);
        return new SelectQuery(tableName);
    }

    public WhereQuery buildWhereQuery(long id) {
        WhereQuery whereQuery = new WhereQuery();
        whereQuery.addKey("id", new ValueClause(id, BigInt.getInstance()), ConditionType.IS);
        return whereQuery;
    }

    public DeleteQuery buildDeleteQuery(Class<?> cls) {
        TableName tableName = getTableNameFromClassUseCase.execute(cls);
        return new DeleteQuery(tableName);
    }


}
