package persistence.sql.dml.h2;

import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.dml.TableDmlQueryBuilder;

public class H2DmlGenerator implements DmlGenerator {

    private final EntityMetadataModelHolder entityMetadataModelHolder;

    private final TableDmlQueryBuilder tableDmlQueryBuilder;

    public H2DmlGenerator(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.entityMetadataModelHolder = entityMetadataModelHolder;
        this.tableDmlQueryBuilder = new H2TableDmlQueryBuilder();
    }

    @Override
    public String insert(Object entity) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(entity.getClass());

        return tableDmlQueryBuilder.createInsertQuery(entityMetadataModel, entity);
    }
}
