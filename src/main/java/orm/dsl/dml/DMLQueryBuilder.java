package orm.dsl.dml;

import jdbc.JdbcTemplate;
import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.QueryRunner;
import orm.dsl.step.dml.DeleteFromStep;
import orm.dsl.step.dml.InsertIntoStep;
import orm.settings.JpaSettings;

public class DMLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;
    private final QueryRunner queryRunner;

    public DMLQueryBuilder(JpaSettings settings, QueryRunner queryRunner) {
        this.settings = settings;
        this.queryRunner = queryRunner;
    }

    public DMLQueryBuilder() {
        this(JpaSettings.ofDefault(), new QueryRunner());
    }

    public DMLQueryBuilder(JpaSettings settings) {
        this(settings, new QueryRunner());
    }

    public DMLQueryBuilder(JdbcTemplate jdbcTemplate) {
        this(JpaSettings.ofDefault(), new QueryRunner(jdbcTemplate));
    }

    public <E> InsertIntoStep insertInto(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect(), queryRunner)
                .buildInsert(new TableEntity<>(entityClass, settings));
    }

    public <E> DeleteFromStep deleteFrom(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect(), queryRunner)
                .buildDelete(new TableEntity<>(entityClass, settings));
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
