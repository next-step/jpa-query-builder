package persistence.entity;

import database.Database;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

public class SimpleEntityManger implements EntityManager {

    private final Database database;

    public SimpleEntityManger(Database database) {
        this.database = database;
    }

    @Override
    public <T> T find(Class<T> clazz, Object id) {
        DMLQueryBuilder dmlQueryBuilder = getDmlQueryBuilder(clazz);

        String findByIdQuery = dmlQueryBuilder.buildFindByIdQuery(id);

        return database.executeQueryForObject(clazz, findByIdQuery);
    }

    @Override
    public void persist(Object entity) {
        Class<?> clazz = entity.getClass();
        DMLQueryBuilder dmlQueryBuilder = getDmlQueryBuilder(clazz);

        String insertQuery = dmlQueryBuilder.buildInsertQuery(entity);

        database.execute(insertQuery);
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();

        Table table = new Table(clazz);
        PKColumn pkColumn = table.getPKColumn();

        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        Object id = getEntityId(entity, pkColumn);
        String deleteByIdQuery = dmlQueryBuilder.buildDeleteByIdQuery(id);

        database.execute(deleteByIdQuery);
    }

    private Object getEntityId(Object entity, PKColumn pkColumn) {
        EntityBinder entityBinder = new EntityBinder(entity);
        return entityBinder.getValue(pkColumn);
    }

    private DMLQueryBuilder getDmlQueryBuilder(Class<?> clazz) {
        Table table = new Table(clazz);
        return new DMLQueryBuilder(table);
    }
}
