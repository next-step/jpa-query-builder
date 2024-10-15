package orm.dsl.dml;

import orm.QueryProvider;
import orm.SQLDialect;
import orm.settings.JpaSettings;
import orm.settings.SnakeForPropertyNamingStrategy;

public class DMLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;

    public DMLQueryBuilder() {
        this.settings = JpaSettings.ofDefault();
    }

    public DMLQueryBuilder(JpaSettings settings) {
        this.settings = settings;
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
