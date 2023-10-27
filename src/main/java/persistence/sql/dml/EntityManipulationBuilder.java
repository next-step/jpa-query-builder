package persistence.sql.dml;

import persistence.sql.ddl.EntityMetadata;
import persistence.sql.ddl.dialect.Dialect;
import utils.CustomStringBuilder;

import static utils.CustomStringBuilder.*;

public class EntityManipulationBuilder {

    private final EntityMetadata entityMetadata;

    public EntityManipulationBuilder(Class<?> type, Dialect dialectParam) {
        this.entityMetadata = new EntityMetadata(type, dialectParam );
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
        return toFindAllStatement(entityMetadata.getTableName(), entityMetadata.getColumnNames(type));
    }

    public String findById(Class<?> type, long id) {
        return toFindByIdStatement(
                entityMetadata.getTableName(),
                entityMetadata.getColumnNames(type),
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
