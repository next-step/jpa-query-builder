package orm.dsl;

import orm.SQLDialect;

public interface QueryProvider {
    SQLDialect dialect();
}
