package persistence.entity;

import persistence.sql.dml.DmlQueryBuilder;
import persistence.sql.domain.Query;
import persistence.sql.domain.QueryResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EntityManagerImpl implements EntityManager {

    private final Connection connection;

    private final DmlQueryBuilder dmlQueryBuilder;

    public EntityManagerImpl(Connection connection, DmlQueryBuilder dmlQueryBuilder) {
        this.connection = connection;
        this.dmlQueryBuilder = dmlQueryBuilder;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        Query query = dmlQueryBuilder.findById(clazz, id);

        return executeQueryForEntity(clazz, query);
    }

    private <T> T executeQueryForEntity(Class<T> clazz, Query query) {
        System.out.println("query.getSql() = " + query.getSql());
        try (final ResultSet resultSet = connection.prepareStatement(query.getSql()).executeQuery()) {
            QueryResult queryResult = new QueryResult(resultSet, query.getTable());
            return queryResult.getSingleEntity(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void persist(Object entity) {
        Query query = dmlQueryBuilder.insert(entity);

        executeQuery(query);
    }

    @Override
    public void remove(Object entity) {
        Query query = dmlQueryBuilder.delete(entity);

        executeQuery(query);
    }

    private void executeQuery(Query query) {
        System.out.println("query = " + query.getSql());
        try (final Statement statement = connection.createStatement()) {
            statement.execute(query.getSql());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
