package persistence.sql.dml;

import persistence.sql.DialectQueryBuilder;
import persistence.sql.dbms.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DMLQueryBuilder<E> extends DialectQueryBuilder<E> {
    protected List<String> defaultColumnsClause;

    protected DMLQueryBuilder(Dialect dialect, Class<E> entityClass) {
        super(dialect, entityClass);
        this.defaultColumnsClause = initDefaultColumnsClause();
    }

    private List<String> initDefaultColumnsClause() {
        return entityTable.getColumns()
                .stream()
                .map(this::createColumnNameDefinition)
                .collect(Collectors.toList());
    }


}
