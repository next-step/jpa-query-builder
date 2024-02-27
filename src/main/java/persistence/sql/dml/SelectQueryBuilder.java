package persistence.sql.dml;

import persistence.sql.mapping.TableData;

public class SelectQueryBuilder {
    private final TableData table;

    public SelectQueryBuilder(Class<?> clazz) {
        this.table = TableData.from(clazz);
    }

    public String toQuery(WhereBuilder whereBuilder) {
        StringBuilder query = new StringBuilder();
        query.append("select ");
        query.append(selectClause());
        query.append(" from ");
        query.append(table.getName());

        if(whereBuilder.isEmpty()) {
            return query.toString();
        }

        query.append(" where ");
        query.append(whereBuilder.toClause());

        return query.toString();
    }

    private String selectClause() {
        return "*";
    }
}
