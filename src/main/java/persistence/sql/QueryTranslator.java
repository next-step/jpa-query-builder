package persistence.sql;


import java.lang.reflect.Field;
import persistence.sql.ddl.CreateQueryTranslator;
import persistence.sql.ddl.DropQueryTranslator;
import persistence.sql.ddl.TableQueryBuilder;
import persistence.sql.dml.DeleteQueryTranslator;
import persistence.sql.dml.InsertQueryTranslator;
import persistence.sql.dml.SelectQueryTranslator;

public class QueryTranslator extends AbstractQueryTranslator {

    private final TableQueryBuilder tableQueryBuilder;

    private final SelectQueryTranslator selectQueryBuilder;

    private final DeleteQueryTranslator deleteQueryBuilder;

    private final InsertQueryTranslator insertQueryTranslator;

    private final DropQueryTranslator dropQueryTranslator;

    private final CreateQueryTranslator createQueryBuilder;

    public QueryTranslator() {
        this(new TableQueryBuilder());
    }

    public QueryTranslator(TableQueryBuilder tableQueryBuilder) {
        this.tableQueryBuilder = tableQueryBuilder;
        this.selectQueryBuilder = new SelectQueryTranslator(tableQueryBuilder);
        this.deleteQueryBuilder = new DeleteQueryTranslator(tableQueryBuilder);
        this.insertQueryTranslator = new InsertQueryTranslator(tableQueryBuilder);
        this.dropQueryTranslator = new DropQueryTranslator(tableQueryBuilder);
        this.createQueryBuilder = new CreateQueryTranslator(tableQueryBuilder);
    }

    public String getCreateTableQuery(final Class<?> entityClass) {
        return createQueryBuilder.getCreateTableQuery(entityClass);
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return dropQueryTranslator.getDropTableQuery(entityClass);
    }

    public String getInsertQuery(Object entity) {
        return insertQueryTranslator.getInsertQuery(entity);
    }

    public String getSelectAllQuery(Class<?> entityClass) {
        return selectQueryBuilder.getSelectAllQuery(entityClass);
    }

    public String getSelectByIdQuery(Class<?> entityClass, Object id) {
        return selectQueryBuilder.getSelectByIdQuery(entityClass, id);
    }

    public String getSelectCountQuery(Class<?> entityClass) {
        return selectQueryBuilder.getSelectCountQuery(entityClass);
    }

    public String getDeleteAllQuery(Class<?> entityClass) {
        return deleteQueryBuilder.getDeleteAllQuery(entityClass);
    }

    public String getDeleteByIdQuery(Class<?> entityClass, Object id) {
        return deleteQueryBuilder.getDeleteByIdQuery(entityClass, id);
    }

    public String getDeleteQueryFromEntity(Object entity) {
        return deleteQueryBuilder.getDeleteQueryFromEntity(entity);
    }

    public String getTableNameFrom(Class<?> entityClass) {
        return tableQueryBuilder.getTableNameFrom(entityClass);
    }

    public String getColumnDefinitionFrom(Field field) {
        return createQueryBuilder.getColumnDefinitionFrom(field);
    }

    public String getColumnDefinitionsFrom(Class<?> entityClass) {
        return createQueryBuilder.getColumnDefinitionsFrom(entityClass);
    }
}
