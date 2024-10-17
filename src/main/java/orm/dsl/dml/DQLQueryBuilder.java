package orm.dsl.dml;

import jdbc.JdbcTemplate;
import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.QueryExecutor;
import orm.dsl.step.dml.SelectFromStep;
import orm.settings.JpaSettings;

public class DQLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;
    private final QueryExecutor queryExecutor;

    public DQLQueryBuilder() {
        this(JpaSettings.ofDefault(), new QueryExecutor());
    }

    public DQLQueryBuilder(JpaSettings settings, QueryExecutor queryExecutor) {
        this.settings = settings;
        this.queryExecutor = queryExecutor;
    }

    public DQLQueryBuilder(JdbcTemplate jdbcTemplate) {
        this(JpaSettings.ofDefault(), new QueryExecutor(jdbcTemplate));
    }

    public DQLQueryBuilder(JdbcTemplate jdbcTemplate, JpaSettings settings) {
        this(settings, new QueryExecutor(jdbcTemplate));
    }

    public <E> SelectFromStep<E> selectFrom(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect(), queryExecutor)
                .buildSelect(new TableEntity<>(entityClass, settings));
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
