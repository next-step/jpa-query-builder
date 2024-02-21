package persistence.sql.ddl;

import persistence.sql.ddl.domain.DatabaseColumn;
import persistence.sql.ddl.domain.DatabaseTable;
import persistence.sql.ddl.view.QueryResolver;

import java.util.List;

import static persistence.sql.ddl.CommonConstant.END_STR;
import static persistence.sql.ddl.CommonConstant.SPACE;
import static persistence.sql.ddl.columntype.MySQLColumnType.CLOSE_BRACKET;
import static persistence.sql.ddl.columntype.MySQLColumnType.OPEN_BRACKET;

public class DdlQueryBuilder implements DdlQueryBuild {

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE";

    private final QueryResolver queryResolver;

    protected DdlQueryBuilder(QueryResolver queryResolver) {
        this.queryResolver = queryResolver;
    }

    @Override
    public String createQuery(Class<?> type) {
        DatabaseTable table = new DatabaseTable(type);
        List<DatabaseColumn> columns = table.getColumns();

        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE)
                .append(SPACE)
                .append(table.getName())
                .append(OPEN_BRACKET);

        return sb.append(queryResolver.toQuery(columns))
                .append(CLOSE_BRACKET)
                .append(END_STR)
                .toString();
    }


    @Override
    public String dropQuery(Class<?> type) {
        DatabaseTable table = new DatabaseTable(type);

        return DROP_TABLE +
                SPACE +
                table.getName() +
                END_STR;
    }
}
