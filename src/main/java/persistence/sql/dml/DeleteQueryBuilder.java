package persistence.sql.dml;

import persistence.sql.mapping.TableData;
import persistence.sql.mapping.TableExtractor;

public class DeleteQueryBuilder {
    private final TableData table;

    public DeleteQueryBuilder(Class<?> clazz) {
        this.table = new TableExtractor(clazz).createTable();
    }

    public String toQuery(WhereBuilder whereBuilder) {
        StringBuilder query = new StringBuilder();
        query.append("delete from ");
        query.append(table.getName());

        if(whereBuilder.isEmpty()) {
            return query.toString();
        }

        query.append(" where ");
        query.append(whereBuilder.toClause());

        return query.toString();
    }
}
