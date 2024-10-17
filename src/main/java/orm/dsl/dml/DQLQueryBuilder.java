package orm.dsl.dml;

import jdbc.JdbcTemplate;
import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.QueryRunner;
import orm.dsl.step.dml.SelectFromStep;
import orm.settings.JpaSettings;

public class DQLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;
    private final QueryRunner queryRunner;

    public DQLQueryBuilder() {
        this(JpaSettings.ofDefault(), new QueryRunner());
    }

    public DQLQueryBuilder(JpaSettings settings, QueryRunner queryRunner) {
        this.settings = settings;
        this.queryRunner = queryRunner;
    }

    public DQLQueryBuilder(JdbcTemplate jdbcTemplate) {
        this(JpaSettings.ofDefault(), new QueryRunner(jdbcTemplate));
    }

    public DQLQueryBuilder(JdbcTemplate jdbcTemplate, JpaSettings settings) {
        this(settings, new QueryRunner(jdbcTemplate));
    }

    public <E> SelectFromStep<E> selectFrom(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect(), queryRunner)
                .buildSelect(new TableEntity<>(entityClass, settings));
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
