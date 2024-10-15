package persistence.sql.dml.clause;

import persistence.model.EntityColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindOptionBuilder {
    private List<EntityColumn> selectingColumns = new ArrayList<>();
    private final List<WhereClause> whereClauses = new ArrayList<>();

    public FindOptionBuilder selectColumns(EntityColumn... selectingColumn) {
        this.selectingColumns = Arrays.stream(selectingColumn).toList();
        return this;
    }

    public FindOptionBuilder where(EqualClause equalClause) {
        this.whereClauses.add(new WhereClause(equalClause));
        return this;
    }

    public FindOptionBuilder where(EqualClause... equalClause) {
        this.whereClauses.add(new WhereClause(Arrays.stream(equalClause).toList()));
        return this;
    }

    public FindOption build() {
        return new FindOption(selectingColumns, whereClauses);
    }
}
