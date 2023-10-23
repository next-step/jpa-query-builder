package persistence.sql.dml;

import persistence.sql.DbmsQueryBuilder;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityColumn;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DMLQueryBuilder<E> extends DbmsQueryBuilder<E> {
    protected List<String> defaultColumnsClause;

    protected DMLQueryBuilder(DbmsStrategy dbmsStrategy, Class<E> entityClass) {
        super(dbmsStrategy, entityClass);
        this.defaultColumnsClause = initDefaultColumnsClause();
    }

    private List<String> initDefaultColumnsClause() {
        return entityTable.getColumns()
                .stream()
                .map(this::createColumnNameDefinition)
                .collect(Collectors.toList());
    }


}
