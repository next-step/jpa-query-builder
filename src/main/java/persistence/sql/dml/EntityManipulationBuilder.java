package persistence.sql.dml;

import persistence.sql.ddl.EntityMetadata;
import utils.CustomStringBuilder;

import static utils.CustomStringBuilder.*;

public class EntityManipulationBuilder {

    private final EntityMetadata entityMetadata;

    public EntityManipulationBuilder(Class<?> type) {
        this.entityMetadata = new EntityMetadata(type);
    }

    public String insert(Object entity) {
        return new CustomStringBuilder()
                .append(columnsClause(entity))
                .append(valuesClause(entity))
                .toString();
    }

    private String columnsClause(Object entity) {
        return toInsertColumnsClause(entityMetadata.getTableName(), entityMetadata.getColumnNames(entity));
    }

    private String valuesClause(Object entity) {
        return toInsertValuesClause(entityMetadata.getValueFrom(entity));
    }

    public String findAll(Class<?> type) {
        return toFindAllStatement(entityMetadata.getColumnNames(type), entityMetadata.getTableName());
    }

    public String findById(Class<?> type, long id) {
        return toFindByIdStatement(
                entityMetadata.getColumnNames(type),
                entityMetadata.getTableName(),
                entityMetadata.getIdColumnName(type),
                String.valueOf(id)
        );
    }

    public String delete(Class<?> type, long id) {
        return toDeleteStatement(
                entityMetadata.getTableName(),
                entityMetadata.getIdColumnName(type),
                String.valueOf(id));
    }

}
