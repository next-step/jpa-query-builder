package persistence.sql.dml.impl;

import persistence.sql.dml.Database;
import persistence.sql.dml.EntityManager;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.data.QueryType;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.logging.Logger;

public class DefaultEntityManager implements EntityManager {
    private static final Logger logger = Logger.getLogger(DefaultEntityManager.class.getName());

    private final Database database;

    public DefaultEntityManager(Database database) {
        this.database = database;
    }

    @Override
    public <T> void persist(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }

        MetadataLoader<?> loader = new SimpleMetadataLoader<>(entity.getClass());

        if (!isNew(entity, loader)) {
            merge(entity);
            return;
        }

        String insertQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.INSERT, loader, entity);
        database.executeUpdate(insertQuery);
    }

    private <T> boolean isNew(Object entity, MetadataLoader<T> loader) {
        try {
            Field primaryKeyField = loader.getPrimaryKeyField();
            primaryKeyField.setAccessible(true);
            Object idValue = primaryKeyField.get(entity);
            if (idValue == null) {
                return true;
            }

            return find(loader.getEntityType(), idValue) == null;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    @Override
    public <T> void merge(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }

        MetadataLoader<?> loader = new SimpleMetadataLoader<>(entity.getClass());

        String mergeQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.UPDATE, loader, entity);
        database.executeUpdate(mergeQuery);
    }

    @Override
    public <T> void remove(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }

        MetadataLoader<?> loader = new SimpleMetadataLoader<>(entity.getClass());

        String removeQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.DELETE, loader, entity);
        database.executeUpdate(removeQuery);
    }

    @Override
    public <T> T find(Class<T> returnType, Object primaryKey) {
        if (primaryKey == null) {
            throw new IllegalArgumentException("Primary key must not be null");
        }

        MetadataLoader<T> loader = new SimpleMetadataLoader<>(returnType);

        String findQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.SELECT, loader, primaryKey);

        return database.executeQuery(findQuery, rs -> mapRowResultSetToEntity(rs, loader));
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        MetadataLoader<T> loader = new SimpleMetadataLoader<>(entityClass);

        String findAllQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.SELECT, loader, null);
        try (ResultSet resultSet = database.executeQuery(findAllQuery)) {

            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapRowResultSetToEntity(resultSet, loader));
            }

            return entities;
        } catch (SQLException e) {
            logger.severe("Failed to find all entities");
            throw new NoSuchElementException(e);
        }
    }

    private <T> T mapRowResultSetToEntity(ResultSet resultSet, MetadataLoader<T> loader) {
        try {
            T entity = loader.getNoArgConstructor().newInstance();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                Object columnValue = resultSet.getObject(i);

                Field field = loader.getField(i - 1);
                field.setAccessible(true);
                field.set(entity, columnValue);
            }

            return entity;

        } catch (ReflectiveOperationException | SQLException e) {
            logger.severe("Failed to map row to entity");
            throw new RuntimeException(e);
        }
    }
}
