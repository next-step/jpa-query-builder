package orm.settings;

import orm.SQLDialect;

public class JpaSettings {

    private SQLDialect dialect;
    private NamingStrategy namingStrategy;

    public static JpaSettings ofDefault() {
        return new JpaSettings()
                .withDialect(SQLDialect.H2)
                .withNamingStrategy(new SnakeForPropertyNamingStrategy());
    }

    public JpaSettings withDialect(SQLDialect dialect) {
        this.dialect = dialect;
        return this;
    }

    public JpaSettings withNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
        return this;
    }

    public SQLDialect getDialect() {
        return dialect;
    }

    public NamingStrategy getNamingStrategy() {
        return namingStrategy;
    }
}
