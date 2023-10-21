package persistence.sql.dml.where;

import persistence.core.EntityMetadataModel;
import persistence.exception.NotEqualEntityTypeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FetchWhereQuery {

    private static final String BLANK = " ";

    private final Class<?> entityType;

    private final Map<String, WhereQuery> whereQueries;

    private final WhereClauseType whereClauseType;

    public FetchWhereQuery(List<WhereQuery> whereQueries, WhereClauseType whereClauseType) {
        this.entityType = whereQueries.get(0).getEntityType();

        this.whereQueries = whereQueries.stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                WhereQuery::getTargetColumn,
                                Function.identity()
                        )
                );
        this.whereClauseType = whereClauseType;
    }

    public String makeWhereQuery(EntityMetadataModel entityMetadataModel) {
        if (!entityMetadataModel.isSameEntityType(entityType)) {
            throw new NotEqualEntityTypeException(entityType, entityMetadataModel.getEntityType());
        }

        List<String> whereClauses = new ArrayList<>();
        
        if (hasPrimaryKey(entityMetadataModel)) {
            whereClauses.add(makeWhereClause(
                    entityMetadataModel.getPrimaryKeyColumn().getName(),
                    whereQueries.get(entityMetadataModel.getPrimaryKeyColumn().getName()).getTargetValue(),
                    whereQueries.get(entityMetadataModel.getPrimaryKeyColumn().getName()).getWhereClauseType())
            );
        }

        whereClauses.addAll(entityMetadataModel.getColumns()
                .stream()
                .filter(targetColumn -> !targetColumn.hasTransient())
                .filter(targetColumn -> whereQueries.containsKey(targetColumn.getName()))
                .map(targetColumn -> makeWhereClause(
                        targetColumn.getName(),
                        whereQueries.get(targetColumn.getName()).getTargetValue(),
                        whereQueries.get(targetColumn.getName()).getWhereClauseType())
                ).collect(Collectors.toList())
        );

        return String.join(getRootSeparationWhereClauseType(), whereClauses);
    }

    private boolean hasPrimaryKey(EntityMetadataModel entityMetadataModel) {
        return whereQueries.get(entityMetadataModel.getPrimaryKeyColumn().getName()) != null;
    }

    private String makeWhereClause(String fieldName, Object value, WhereClauseType whereClauseType) {
        return fieldName +
                BLANK +
                whereClauseType.getOperator() +
                BLANK +
                convertToString(value);
    }

    private String convertToString(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }

        return String.valueOf(value);
    }
    
    private String getRootSeparationWhereClauseType() {
        return BLANK + whereClauseType.getOperator() + BLANK;
    }
}
