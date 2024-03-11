package persistence.sql;


import java.lang.reflect.Field;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.ddl.TableQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

public class QueryBuilder extends AbstractQueryBuilder {

    private final TableQueryBuilder tableQueryBuilder;

    private final SelectQueryBuilder selectQueryBuilder;

    private final DeleteQueryBuilder deleteQueryBuilder;

    private final InsertQueryBuilder insertQueryTranslator;

    private final DropQueryBuilder dropQueryBuilder;

    private final CreateQueryBuilder createQueryBuilder;

    public QueryBuilder() {
        this(new TableQueryBuilder());
    }

    public QueryBuilder(TableQueryBuilder tableQueryBuilder) {
        this.tableQueryBuilder = tableQueryBuilder;
        this.selectQueryBuilder = new SelectQueryBuilder(tableQueryBuilder);
        this.deleteQueryBuilder = new DeleteQueryBuilder(tableQueryBuilder);
        this.insertQueryTranslator = new InsertQueryBuilder(tableQueryBuilder);
        this.dropQueryBuilder = new DropQueryBuilder(tableQueryBuilder);
        this.createQueryBuilder = new CreateQueryBuilder(tableQueryBuilder);
    }

    public String getCreateTableQuery(final Class<?> entityClass) {
        return createQueryBuilder.getCreateTableQuery(entityClass);
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return dropQueryBuilder.getDropTableQuery(entityClass);
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
