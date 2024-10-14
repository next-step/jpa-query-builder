package orm.dsl.ddl;

import orm.SQLDialect;
import orm.TableEntity;
import orm.settings.JpaSettings;
import orm.dsl.ddl.dialect.h2.H2CreateTableImpl;
import orm.dsl.ddl.dialect.h2.H2DropTableImpl;
import orm.settings.SnakeForPropertyNamingStrategy;

public class DDLQueryBuilder {

    private final JpaSettings settings;

    public DDLQueryBuilder() {
        this.settings = new JpaSettings()
                .withDialect(SQLDialect.H2)
                .withNamingStrategy(new SnakeForPropertyNamingStrategy());
    }

    public <ENTITY> CreateTableStep createTable(Class<ENTITY> entityClass) {
        return new H2CreateTableImpl<>(new TableEntity<>(entityClass, settings));
    }

    public <ENTITY> DropTableStep dropTable(Class<ENTITY> entityClass) {
        return new H2DropTableImpl<>(new TableEntity<>(entityClass, settings));
    }
}
