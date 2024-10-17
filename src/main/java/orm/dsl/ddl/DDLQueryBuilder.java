package orm.dsl.ddl;

import jdbc.JdbcTemplate;
import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.QueryRunner;
import orm.dsl.step.ddl.CreateTableStep;
import orm.dsl.step.ddl.DropTableStep;
import orm.settings.JpaSettings;

public class DDLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;
    private final QueryRunner queryRunner;

    public DDLQueryBuilder(JpaSettings settings, QueryRunner queryRunner) {
        this.settings = settings;
        this.queryRunner = queryRunner;
    }

    public DDLQueryBuilder() {
        this(JpaSettings.ofDefault(), new QueryRunner());
    }

    public DDLQueryBuilder(JpaSettings settings) {
        this(settings, new QueryRunner());
    }

    public DDLQueryBuilder(JdbcTemplate jdbcTemplate) {
        this(JpaSettings.ofDefault(), new QueryRunner(jdbcTemplate));
    }

    public <ENTITY> CreateTableStep createTable(Class<ENTITY> entityClass) {
        return new ImplQueryBuilder(dialect(), queryRunner)
                .buildCreateTable(new TableEntity<>(entityClass, settings));
    }

    public <ENTITY> DropTableStep dropTable(Class<ENTITY> entityClass) {
        return new ImplQueryBuilder(dialect(), queryRunner)
                .buildDropTable(new TableEntity<>(entityClass, settings));
    }

    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
