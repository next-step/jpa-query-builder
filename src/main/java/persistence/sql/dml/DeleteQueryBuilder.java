package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Table;

import java.util.List;

public class DeleteQueryBuilder implements QueryBuilder {

    private final static String DELETE_QUERY = "DELETE FROM %s%s;";

    private final Table table;

    private final WhereQueryBuilder whereQueryBuilder;

    public DeleteQueryBuilder(Class<?> clazz, List<String> whereColumns, List<Object> whereValues, List<String> whereOperators) {
        this.table = new Table(clazz);
        this.whereQueryBuilder = new WhereQueryBuilder(clazz, whereColumns, whereValues, whereOperators);
    }

    @Override
    public String build() {
        return String.format(
                DELETE_QUERY,
                table.getName(),
                whereQueryBuilder.build()
        );
    }
}
