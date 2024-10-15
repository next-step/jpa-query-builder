package orm.dsl.dialect;

import orm.SQLDialect;
import orm.dsl.ddl.CreateTableImpl;
import orm.dsl.ddl.DropTableImpl;
import orm.dsl.dialect.h2.H2CreateTableImpl;
import orm.dsl.dialect.h2.H2DropTableImpl;

import java.util.Map;

public class DialectStatementMap {

    private final Map<SQLDialect, Class<? extends CreateTableImpl>> createTableImplMap;
    private final Map<SQLDialect, Class<? extends DropTableImpl>> dropTableImplMap;

    public DialectStatementMap() {
        this.createTableImplMap = initSelectImplMap();
        this.dropTableImplMap = initDropImplMap();
    }

    public Class<? extends CreateTableImpl> getCreateTableImpl(SQLDialect dialect) {
        return createTableImplMap.get(dialect);
    }

    public Class<? extends DropTableImpl> getDropTableImpl(SQLDialect dialect) {
        return dropTableImplMap.get(dialect);
    }

    private Map<SQLDialect, Class<? extends CreateTableImpl>> initSelectImplMap() {
        return Map.ofEntries(
                Map.entry(SQLDialect.H2, H2CreateTableImpl.class)
        );
    }

    private Map<SQLDialect, Class<? extends DropTableImpl>> initDropImplMap() {
        return Map.ofEntries(
                Map.entry(SQLDialect.H2, H2DropTableImpl.class)
        );
    }
}
