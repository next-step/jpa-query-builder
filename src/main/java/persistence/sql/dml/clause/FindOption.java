package persistence.sql.dml.clause;

import persistence.model.EntityColumn;
import persistence.sql.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public class FindOption {
    private final List<EntityColumn> selectingColumns;
    private final List<WhereClause> whereClauses;

    public FindOption(List<EntityColumn> selectingColumns, List<WhereClause> whereClauses) {
        this.selectingColumns = selectingColumns;
        this.whereClauses = whereClauses;
    }

    public List<WhereClause> getWhere() {
        return whereClauses;
    }

    public List<EntityColumn> getSelectingColumns() {
        return selectingColumns;
    }

    public List<String> getSelectingColumnNames() {
        String SELECT_ALL = "*";
        List<String> selectingColumnNames = selectingColumns.stream()
                .map(EntityColumn::getName)
                .toList();

        return selectingColumnNames.isEmpty()
                ? List.of(SELECT_ALL)
                : selectingColumnNames;
    }

    public String joinWhereClauses(Dialect dialect) {
        String whereQuery = "WHERE ";

        if (whereClauses.isEmpty()) {
            return "";
        }
        return whereQuery + whereClauses.stream()
                .map(where -> where.toSql(dialect))
                .collect(Collectors.joining(" OR "));
    }
}
