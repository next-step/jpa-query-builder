package orm.dsl;

import orm.SQLDialect;

public class JpaSettings {

    private SQLDialect dialect;

    public JpaSettings withDialect(SQLDialect dialect) {
        this.dialect = SQLDialect.H2;
        return this;
    }

    public SQLDialect getDialect() {
        return dialect;
    }
}
