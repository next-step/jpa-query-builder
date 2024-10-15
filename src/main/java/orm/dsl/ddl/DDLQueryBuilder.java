package orm.dsl.ddl;

import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.step.ddl.CreateTableStep;
import orm.dsl.step.ddl.DropTableStep;
import orm.settings.JpaSettings;

public class DDLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;

    public DDLQueryBuilder() {
        this.settings = JpaSettings.ofDefault();
    }

    public DDLQueryBuilder(JpaSettings settings) {
        this.settings = settings;
    }

    public <ENTITY> CreateTableStep createTable(Class<ENTITY> entityClass) {
        return new ImplQueryBuilder(dialect())
                .buildCreateTable(new TableEntity<>(entityClass, settings));
    }

    public <ENTITY> DropTableStep dropTable(Class<ENTITY> entityClass) {
        return new ImplQueryBuilder(dialect())
                .buildDropTable(new TableEntity<>(entityClass, settings));
    }

    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
