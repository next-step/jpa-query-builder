package orm.dsl;

import jdbc.JdbcTemplate;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.step.ddl.CreateTableStep;
import orm.dsl.step.ddl.DropTableStep;
import orm.dsl.step.dml.DeleteFromStep;
import orm.dsl.step.dml.InsertIntoStep;
import orm.dsl.step.dml.SelectFromStep;
import orm.settings.JpaSettings;

public class QueryBuilder implements QueryProvider {

    private final JpaSettings settings;
    private final QueryRunner queryRunner;

    public QueryBuilder() {
        this(JpaSettings.ofDefault(), new QueryRunner());
    }

    public QueryBuilder(JpaSettings settings) {
        this(settings, new QueryRunner());
    }

    public QueryBuilder(JdbcTemplate jdbcTemplate) {
        this(JpaSettings.ofDefault(), new QueryRunner(jdbcTemplate));
    }

    public QueryBuilder(JpaSettings jpaSettings, QueryRunner queryRunner) {
        this.settings = jpaSettings;
        this.queryRunner = queryRunner;
    }

    public <E> CreateTableStep createTable(Class<E> entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .createTable(new TableEntity<>(entityClass, settings));
    }

    public <E> DropTableStep dropTable(Class<E> entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .dropTable(new TableEntity<>(entityClass, settings));
    }

    public <E> SelectFromStep<E> selectFrom(Class<E> entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .selectFrom(new TableEntity<>(entityClass, settings));
    }

    public <E> InsertIntoStep<E> insertInto(Class<E> entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .insert(new TableEntity<>(entityClass, settings));
    }

    public <E> InsertIntoStep<E> insertInto(E entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .insert(new TableEntity<>(entityClass, settings));
    }

    public <E> DeleteFromStep deleteFrom(Class<E> entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .deleteFrom(new TableEntity<>(entityClass, settings));
    }

    public <E> DeleteFromStep deleteFrom(E entityClass) {
        return new DialectStatementLocator(dialect(), queryRunner)
                .deleteFrom(new TableEntity<>(entityClass, settings));
    }

    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
