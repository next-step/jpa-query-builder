package persistence.sql.ddl.view.mysql;

import persistence.sql.MySqlDialect;
import persistence.sql.ddl.view.AbstractQueryResolver;
import persistence.sql.domain.DatabasePrimaryColumn;

import static persistence.sql.CommonConstant.SPACE;

public class MySQLPrimaryKeyResolver extends AbstractQueryResolver {

    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String NOT_NULL = "NOT NULL";

    public MySQLPrimaryKeyResolver() {
        super(new MySqlDialect());
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
