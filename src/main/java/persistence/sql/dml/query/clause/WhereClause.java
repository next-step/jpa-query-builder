package persistence.sql.dml.query.clause;

import persistence.sql.entity.model.Criterias;

public class WhereClause {

    private static final String FORMAT = "where %s";
    private static final String EMPTY = "";

    private Criterias criterias;

    public WhereClause(final Criterias criterias) {
        this.criterias = criterias;
    }

    public String toSql() {
        String sql = criterias.toSql();

        return EMPTY.equals(sql) ?
                EMPTY :
                String.format(FORMAT, criterias.toSql());

    }

}
