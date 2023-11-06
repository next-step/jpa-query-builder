package persistence.sql.dml;

import jakarta.persistence.GenerationType;
import persistence.sql.dml.delete.DeleteQuery;
import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.dml.select.SelectQuery;
import persistence.sql.dml.where.ConditionType;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.usecase.GetFieldFromClassUseCase;
import persistence.sql.usecase.GetFieldValueUseCase;
import persistence.sql.usecase.GetIdDatabaseFieldUseCase;
import persistence.sql.usecase.GetTableNameFromClassUseCase;
import persistence.sql.vo.DatabaseField;
import persistence.sql.vo.DatabaseFields;
import persistence.sql.vo.TableName;

public class DataManipulationLanguageGenerator {
    private final GetTableNameFromClassUseCase getTableNameFromClassUseCase;
    private final GetFieldFromClassUseCase getFieldFromClassUseCase;
    private final GetFieldValueUseCase getFieldValueUseCase;
    private final GetIdDatabaseFieldUseCase getIdDatabaseFieldUseCase;

    public DataManipulationLanguageGenerator(GetTableNameFromClassUseCase getTableNameFromClassUseCase, GetFieldFromClassUseCase getFieldFromClassUseCase, GetFieldValueUseCase getFieldValueUseCase, GetIdDatabaseFieldUseCase getIdDatabaseFieldUseCase) {
        this.getTableNameFromClassUseCase = getTableNameFromClassUseCase;
        this.getFieldFromClassUseCase = getFieldFromClassUseCase;
        this.getFieldValueUseCase = getFieldValueUseCase;
        this.getIdDatabaseFieldUseCase = getIdDatabaseFieldUseCase;
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

    public WhereQuery buildSelectWhereQuery(Class<?> cls, long id) {
        WhereQuery whereQuery = new WhereQuery();
        DatabaseField field = getIdDatabaseFieldUseCase.execute(cls);
        whereQuery.addKey(field.getDatabaseFieldName(), new ValueClause(id, field.getDatabaseType()), ConditionType.IS);
        return whereQuery;
    }

    public WhereQuery buildWhereQuery(Object object) {
        WhereQuery whereQuery = new WhereQuery();
        DatabaseField field = getIdDatabaseFieldUseCase.execute(object.getClass());
        Object value = getFieldValueUseCase.execute(object, field);
        if(value == null) {
            throw new NullPointerException("Id should not be null to delete");
        }
        whereQuery.addKey(field.getDatabaseFieldName(), new ValueClause(value, field.getDatabaseType()), ConditionType.IS);
        return whereQuery;
    }

    public DeleteQuery buildDeleteQuery(Class<?> cls) {
        TableName tableName = getTableNameFromClassUseCase.execute(cls);
        return new DeleteQuery(tableName);
    }
}
