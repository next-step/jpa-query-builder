package persistence.sql;


import java.lang.reflect.Field;
import persistence.sql.ddl.ColumnTranslator;
import persistence.sql.ddl.CreateQueryTranslator;
import persistence.sql.ddl.DropQueryTranslator;
import persistence.sql.ddl.TableTranslator;
import persistence.sql.dml.ColumnValueTranslator;
import persistence.sql.dml.DeleteQueryTranslator;
import persistence.sql.dml.InsertQueryTranslator;
import persistence.sql.dml.SelectQueryTranslator;

public class QueryTranslator {

    private final TableTranslator tableTranslator = new TableTranslator();

    private final ColumnTranslator columnTranslator = new ColumnTranslator();

    private final ColumnValueTranslator columnValueTranslator = new ColumnValueTranslator();

    private final SelectQueryTranslator selectQueryTranslator;

    private final DeleteQueryTranslator deleteQueryTranslator;

    private final InsertQueryTranslator insertQueryTranslator;

    private final DropQueryTranslator dropQueryTranslator;

    private final CreateQueryTranslator createQueryTranslator;

    public QueryTranslator() {
        this.selectQueryTranslator = new SelectQueryTranslator(columnTranslator,
            columnValueTranslator, tableTranslator);
        this.deleteQueryTranslator = new DeleteQueryTranslator(columnTranslator,
            columnValueTranslator, tableTranslator);
        this.insertQueryTranslator = new InsertQueryTranslator(columnTranslator,
            columnValueTranslator, tableTranslator);
        this.dropQueryTranslator = new DropQueryTranslator(tableTranslator);
        this.createQueryTranslator = new CreateQueryTranslator(columnTranslator, tableTranslator);
    }

    public String getCreateTableQuery(final Class<?> entityClass) {
        return createQueryTranslator.getCreateTableQuery(entityClass);
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return dropQueryTranslator.getDropTableQuery(entityClass);
    }

    public String getInsertQuery(Object entity) {
        return insertQueryTranslator.getInsertQuery(entity);
    }

    public String getSelectAllQuery(Class<?> entityClass) {
        return selectQueryTranslator.getSelectAllQuery(entityClass);
    }

    public String getSelectByIdQuery(Class<?> entityClass, Object id) {
        return selectQueryTranslator.getSelectByIdQuery(entityClass, id);
    }

    public String getSelectCountQuery(Class<?> entityClass) {
        return selectQueryTranslator.getSelectCountQuery(entityClass);
    }

    public String getDeleteAllQuery(Class<?> entityClass) {
        return deleteQueryTranslator.getDeleteAllQuery(entityClass);
    }

    public String getDeleteByIdQuery(Class<?> entityClass, Object id) {
        return deleteQueryTranslator.getDeleteByIdQuery(entityClass, id);
    }

    public String getDeleteQueryFromEntity(Object entity) {
        return deleteQueryTranslator.getDeleteQueryFromEntity(entity);
    }

    public String getTableNameFrom(Class<?> entityClass) {
        return tableTranslator.getTableNameFrom(entityClass);
    }

    public String getColumnDefinitionFrom(Field field) {
        return columnTranslator.getColumnDefinitionFrom(field);
    }

    public String getColumnDefinitionsFrom(Class<?> entityClass) {
        return columnTranslator.getColumnDefinitionsFrom(entityClass);
    }
}
