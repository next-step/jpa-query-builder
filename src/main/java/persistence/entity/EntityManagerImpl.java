package persistence.entity;

import java.sql.Connection;
import persistence.entity.impl.retrieve.EntityLoader;
import persistence.entity.impl.retrieve.EntityLoaderImpl;
import persistence.entity.impl.store.EntityPersister;
import persistence.entity.impl.store.EntityPersisterImpl;
import persistence.sql.dialect.ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.EqualOperator;
import persistence.sql.dml.statement.DeleteStatementBuilder;
import persistence.sql.dml.statement.InsertStatementBuilder;
import persistence.sql.dml.statement.SelectStatementBuilder;
import persistence.sql.schema.EntityClassMappingMeta;
import persistence.sql.schema.EntityObjectMappingMeta;

public class EntityManagerImpl<T> implements EntityManager<T> {

    private final ColumnType columnType;

    private final EntityLoader<T> entityLoader;
    private final EntityPersister entityPersister;

    public EntityManagerImpl(Class<T> clazz, Connection connection, ColumnType columnType) {
        this.entityLoader = new EntityLoaderImpl<>(clazz, connection, columnType);
        this.entityPersister = new EntityPersisterImpl(connection);
        this.columnType = columnType;
    }

    @Override
    public T find(Class<T> clazz, Long Id) {
        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(clazz, columnType);

        final String selectSql = SelectStatementBuilder.builder()
            .select(clazz, columnType)
            .where(WherePredicate.of(classMappingMeta.getIdFieldColumnName(), Id, new EqualOperator()))
            .build();

        return entityLoader.load(selectSql);
    }

    @Override
    public void persist(Object entity) {
        final InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(columnType);
        final String insertSql = insertStatementBuilder.insert(entity);

        entityPersister.store(insertSql);
    }

    @Override
    public void remove(Object entity) {
        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(entity.getClass(), columnType);
        final EntityObjectMappingMeta objectMappingMeta = EntityObjectMappingMeta.of(entity, classMappingMeta);

        final String deleteSql = DeleteStatementBuilder.builder()
            .delete(entity.getClass(), columnType)
            .where(WherePredicate.of(objectMappingMeta.getIdColumnName(), objectMappingMeta.getIdValue(), new EqualOperator()))
            .build();

        entityPersister.delete(deleteSql);
    }
}
