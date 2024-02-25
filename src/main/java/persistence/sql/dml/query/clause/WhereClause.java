package persistence.sql.dml.query.clause;

import persistence.sql.entity.model.DomainType;

import java.util.Map;
import java.util.stream.Collectors;

public class WhereClause {

    private static final String FORMAT = "where %s";
    private static final String WHERE_FORMAT = "%s='%s'";
    private static final String DELIMITER = "AND";
    private static final String EMPTY = "";

    private final Map<DomainType, String> whereDomains;

    public WhereClause(final Map<DomainType, String> whereDomains) {
        this.whereDomains = whereDomains;
    }

    public String toSql() {
        if(whereDomains.isEmpty()) {
            return EMPTY;
        }

        return String.format(FORMAT, whereDomains.entrySet()
                .stream()
                .map(entry -> String.format(WHERE_FORMAT, entry.getKey().getColumnName(), entry.getValue()))
                .collect(Collectors.joining(DELIMITER)));
    }

}
