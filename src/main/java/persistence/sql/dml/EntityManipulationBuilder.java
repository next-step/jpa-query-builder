package persistence.sql.dml;

import persistence.sql.ddl.EntityMetadata;
import utils.CustomStringBuilder;

import java.sql.ResultSet;

import static utils.CustomStringBuilder.*;

public class EntityManipulationBuilder {

    private EntityMetadata entityMetadata;

    public EntityManipulationBuilder(EntityMetadata entityMetadata) {
        this.entityMetadata = entityMetadata;
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

    public String findAll() {
        return toFindAllStatement(entityMetadata.getTableName(), entityMetadata.getColumnNames());
    }

    public String findById(long id) {
        return toFindByIdStatement(
                entityMetadata.getTableName(),
                entityMetadata.getColumnNames(),
                entityMetadata.getIdColumnName(),
                String.valueOf(id)
        );
    }

    public String delete(String id) {
        return toDeleteStatement(
                entityMetadata.getTableName(),
                entityMetadata.getIdColumnName(),
                id);
    }

    public <T> T getEntity(ResultSet resultSet) {
        return entityMetadata.getEntity(resultSet);
    }

}
