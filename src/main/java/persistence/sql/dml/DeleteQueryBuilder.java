package persistence.sql.dml;

import org.h2.table.Table;
import persistence.sql.mapping.TableData;

public class DeleteQueryBuilder {
    private final TableData table;

    public DeleteQueryBuilder(Class<?> clazz) {
        this.table = TableData.from(clazz);
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
