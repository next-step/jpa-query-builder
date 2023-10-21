package persistence.sql.dml.h2;

import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.dml.InsertQueryBuilder;

import java.util.List;

public class H2DmlGenerator implements DmlGenerator {

    private final EntityMetadataModelHolder entityMetadataModelHolder;

    private final InsertQueryBuilder insertQueryBuilder;

    public H2DmlGenerator(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.entityMetadataModelHolder = entityMetadataModelHolder;
        this.insertQueryBuilder = new H2InsertQueryBuilder();
    }

    @Override
    public String insert(Object entity) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(entity.getClass());

        return insertQueryBuilder.createInsertQuery(entityMetadataModel, entity);
    }

    @Override
    public <T> List<T> findAll(Class<T> entity) {
        return null;
    }
}
