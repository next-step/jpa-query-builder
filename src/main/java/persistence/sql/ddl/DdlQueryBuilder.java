package persistence.sql.ddl;

import persistence.sql.ddl.view.QueryResolver;
import persistence.sql.domain.ColumnOperation;
import persistence.sql.domain.DatabaseTable;

import java.util.List;

public class DdlQueryBuilder implements DdlQueryBuild {

    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s(%s);";
    private static final String DROP_TABLE_TEMPLATE = "DROP TABLE %s;";

    private final QueryResolver queryResolver;

    public DdlQueryBuilder(QueryResolver queryResolver) {
        this.queryResolver = queryResolver;
    }

    @Override
    public String createQuery(Class<?> type) {
        DatabaseTable table = new DatabaseTable(type);
        List<ColumnOperation> columns = table.getAllColumns();

        return String.format(CREATE_TABLE_TEMPLATE, table.getName(), queryResolver.toQuery(columns));
    }


    @Override
    public String dropQuery(Class<?> type) {
        DatabaseTable table = new DatabaseTable(type);

        return String.format(DROP_TABLE_TEMPLATE, table.getName());
    }
}
