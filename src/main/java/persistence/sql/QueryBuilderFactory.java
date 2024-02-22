package persistence.sql;

import persistence.sql.ddl.DatabaseDialect;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.view.mysql.MySQLPrimaryKeyResolver;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.Map;
import java.util.Optional;

public class QueryBuilderFactory {

    private final Map<DatabaseDialect, QueryBuilder> factory;

    public QueryBuilderFactory() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(new MySQLPrimaryKeyResolver());
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

        factory = Map.of(DatabaseDialect.MYSQL, new QueryBuilder(ddlQueryBuilder, dmlQueryBuilder));
    }

    public QueryBuilder getInstance(DatabaseDialect dialect) {
        return Optional.of(factory.get(dialect))
                .orElseThrow(IllegalArgumentException::new);
    }
}
