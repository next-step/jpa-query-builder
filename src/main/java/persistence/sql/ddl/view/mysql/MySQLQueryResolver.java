package persistence.sql.ddl.view.mysql;

import persistence.sql.ddl.columntype.MySQLColumnType;
import persistence.sql.ddl.domain.DatabasePrimaryColumn;
import persistence.sql.ddl.view.AbstractQueryResolver;

import static persistence.sql.ddl.CommonConstant.SPACE;

public class MySQLQueryResolver extends AbstractQueryResolver {

    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String NOT_NULL = "NOT NULL";

    public MySQLQueryResolver() {
        super(MySQLColumnType::convert);
    }

    @Override
    protected String addPrimaryConstraint(DatabasePrimaryColumn primaryColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append(SPACE)
                .append(PRIMARY_KEY);
        if (!primaryColumn.hasIdentityStrategy()) {
            return sb.toString();
        }
        return sb.append(SPACE)
                .append(AUTO_INCREMENT)
                .toString();
    }

    @Override
    protected String addNotNullConstraint() {
        return SPACE + NOT_NULL;
    }

}
