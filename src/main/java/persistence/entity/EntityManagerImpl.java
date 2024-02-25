package persistence.entity;

import persistence.sql.dml.DmlQueryBuilder;
import persistence.sql.domain.Query;
import persistence.sql.domain.QueryResult;

import java.sql.Connection;
import java.sql.ResultSet;

public class EntityManagerImpl implements EntityManager{

    private final Connection connection;

    private final DmlQueryBuilder dmlQueryBuilder;

    public EntityManagerImpl(Connection connection, DmlQueryBuilder dmlQueryBuilder) {
        this.connection = connection;
        this.dmlQueryBuilder = dmlQueryBuilder;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        Query query = dmlQueryBuilder.findById(clazz, id);
        String sql = query.getSql();

        try (final ResultSet resultSet = connection.prepareStatement(sql).executeQuery()) {
            QueryResult queryResult = new QueryResult(resultSet, query.getTable());
            return queryResult.getSingleEntity(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
