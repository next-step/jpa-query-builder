package persistence.sql.dml;

import persistence.sql.mapping.Columns;
import persistence.sql.mapping.TableData;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SelectQueryBuilder {
    private final TableData table;
    private final Columns columns;

    public SelectQueryBuilder(Class<?> clazz) {
        this.table = TableData.from(clazz);
        this.columns = Columns.createColumns(clazz);
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
        ArrayList<String> names = new ArrayList<String>();
        names.add(columns.getKeyColumn().getName());
        names.addAll(columns.getNames());
        return String.join(", ", names);
    }
}
