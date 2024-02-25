package persistence.sql.dml.query.clause;

import persistence.sql.entity.model.DomainType;
import persistence.sql.entity.model.DomainTypes;

import java.util.List;
import java.util.stream.Collectors;

public class ColumnClause {
    private static final String DELIMITER = ",";

    private final List<String> columns;

    private ColumnClause(final List<String> columns) {
        this.columns = columns;
    }

    public List<String> getColumns() {
        return columns;
    }

    public static ColumnClause from(DomainTypes domainTypes) {
        return new ColumnClause(domainTypes.getDomainTypes()
                .stream()
                .filter(DomainType::isNotTransient)
                .map(DomainType::getColumnName)
                .collect(Collectors.toList()));
    }

    public String toSql() {
        return String.join(DELIMITER, columns);
    }


}
