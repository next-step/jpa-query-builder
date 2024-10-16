package persistence.sql.dml.impl;

import jakarta.persistence.Id;
import persistence.sql.clause.Clause;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.clause.InsertColumnValueClause;
import persistence.sql.clause.SetValueClause;
import persistence.sql.clause.WhereConditionalClause;
import persistence.sql.common.util.NameConverter;
import persistence.sql.data.QueryType;
import persistence.sql.dml.Database;
import persistence.sql.dml.EntityManager;
import persistence.sql.dml.MetadataLoader;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class DefaultEntityManager implements EntityManager {
    private static final Logger logger = Logger.getLogger(DefaultEntityManager.class.getName());

    private final Database database;
    private final NameConverter nameConverter;

    public DefaultEntityManager(Database database, NameConverter nameConverter) {
        this.database = database;
        this.nameConverter = nameConverter;
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

        InsertColumnValueClause clause = InsertColumnValueClause.newInstance(entity, nameConverter);

        String insertQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.INSERT, loader, clause);
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
        List<Field> fields = loader.getFieldAllByPredicate(field -> !field.isAnnotationPresent(Id.class));

        Clause[] clauses = fields.stream()
                .map(field -> SetValueClause.newInstance(field, entity, loader.getColumnName(field, nameConverter)))
                .toArray(Clause[]::new);

        String mergeQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.UPDATE, loader, clauses);
        database.executeUpdate(mergeQuery);
    }

    @Override
    public <T> void remove(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }


        MetadataLoader<?> loader = new SimpleMetadataLoader<>(entity.getClass());
        Field pkField = loader.getPrimaryKeyField();
        Object extractedValue = Clause.extractValue(pkField, entity);
        String value = Clause.toColumnValue(extractedValue);

        WhereConditionalClause clause = WhereConditionalClause.builder()
                .column(loader.getColumnName(pkField, nameConverter))
                .eq(value);

        String removeQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.DELETE, loader, clause);
        database.executeUpdate(removeQuery);
    }

    @Override
    public <T> T find(Class<T> returnType, Object primaryKey) {
        if (primaryKey == null) {
            throw new IllegalArgumentException("Primary key must not be null");
        }

        MetadataLoader<T> loader = new SimpleMetadataLoader<>(returnType);
        String value = Clause.toColumnValue(primaryKey);

        WhereConditionalClause clause = WhereConditionalClause.builder()
                .column(loader.getColumnName(loader.getPrimaryKeyField(), nameConverter))
                .eq(value);

        String findQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.SELECT, loader, clause);

        return database.executeQuery(findQuery, resultSet -> {
            if (resultSet.next()) {
                return mapRowResultSetToEntity(resultSet, loader);
            }

            return null;
        });
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        MetadataLoader<T> loader = new SimpleMetadataLoader<>(entityClass);
        String findAllQuery = QueryBuilderFactory.getInstance().buildQuery(QueryType.SELECT, loader);

        return database.executeQuery(findAllQuery, resultSet -> {
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapRowResultSetToEntity(resultSet, loader));
            }

            return entities;
        });
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
