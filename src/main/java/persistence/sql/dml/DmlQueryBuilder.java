package persistence.sql.dml;

public class DmlQueryBuilder implements InsertQueryBuild, SelectQueryBuild, DeleteQueryBuild {

    private final InsertQueryBuild insertQueryBuilder;

    private final SelectQueryBuild selectQueryBuilder;

    private final DeleteQueryBuild deleteQueryBuilder;

    public DmlQueryBuilder(InsertQueryBuild insertQueryBuilder, SelectQueryBuild selectQueryBuilder, DeleteQueryBuild deleteQueryBuilder) {
        this.insertQueryBuilder = insertQueryBuilder;
        this.selectQueryBuilder = selectQueryBuilder;
        this.deleteQueryBuilder = deleteQueryBuilder;
    }

    public DmlQueryBuilder() {
        this.insertQueryBuilder = new InsertQueryBuilder();
        this.selectQueryBuilder = new SelectQueryBuilder();
        this.deleteQueryBuilder = new DeleteQueryBuilder();
    }


    @Override
    public <T> String insert(T entity) {
        return insertQueryBuilder.insert(entity);
    }

    @Override
    public String findAll(Class<?> entity) {
        return selectQueryBuilder.findAll(entity);
    }

    @Override
    public String findById(Class<?> entity, Object id) {
        return selectQueryBuilder.findById(entity, id);

    }

    @Override
    public <T> String delete(T entity) {
        return deleteQueryBuilder.delete(entity);
    }
}
