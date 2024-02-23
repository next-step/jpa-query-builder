package persistence.sql.ddl;

import persistence.sql.AbstractQueryBuilderFactory;
import persistence.sql.DatabaseDialect;
import persistence.sql.ddl.view.mysql.MySQLPrimaryKeyResolver;

import java.util.Map;

public class DdlQueryBuilderFactory extends AbstractQueryBuilderFactory<DdlQueryBuild> {

    public DdlQueryBuilderFactory() {
        super(Map.of(DatabaseDialect.MYSQL, new DdlQueryBuilder(new MySQLPrimaryKeyResolver())));
    }
}
