package persistence.sql.dml;

import persistence.sql.DbmsQueryBuilder;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityColumn;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DMLQueryBuilder<E> extends DbmsQueryBuilder<E> {
    private E entity;
    protected List<String> defaultColumnsClause;
    protected  List<String> defaultValuesClause;
    protected DMLQueryBuilder(DbmsStrategy dbmsStrategy, E e) {
        super(dbmsStrategy, (Class<E>) e.getClass());
        this.entity = e;
        this.defaultColumnsClause = initDefaultColumnsClause();
        this.defaultValuesClause = initDefaultValuesClause();
    }

    private List<String> initDefaultColumnsClause() {
        return entityTable.getColumns()
                .stream()
                .map(this::createColumnNameDefinition)
                .collect(Collectors.toList());
    }

    private List<String> initDefaultValuesClause() {
        return entityTable.getColumns()
                .stream()
                .map(this::getEntityColumnValue)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    protected <V> V getEntityColumnValue(EntityColumn<E, V> entityColumn) {
        if(entityColumn.getType() == String.class) {
            return (V) ("'" + entityColumn.getValue(entity) + "'");
        }

        return entityColumn.getValue(entity);
    }
}
