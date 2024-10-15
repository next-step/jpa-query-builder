package orm.dsl.ddl;

import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ddl.dialect.h2.H2DropTableImpl;
import orm.settings.JpaSettings;
import orm.settings.SnakeForPropertyNamingStrategy;

public class DDLQueryBuilder {

    private final JpaSettings settings;

    public DDLQueryBuilder() {
        this.settings = new JpaSettings()
                .withDialect(SQLDialect.H2)
                .withNamingStrategy(new SnakeForPropertyNamingStrategy());
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
