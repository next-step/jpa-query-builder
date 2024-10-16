package orm.dsl.dialect;

import orm.SQLDialect;
import orm.dsl.ddl.CreateTableImpl;
import orm.dsl.ddl.DropTableImpl;
import orm.dsl.dialect.h2.H2CreateTableImpl;
import orm.dsl.dialect.h2.H2DropTableImpl;
import orm.dsl.dialect.h2.H2InsertImpl;
import orm.dsl.dml.InsertImpl;

import java.util.Map;

public class DialectStatementMap {

    private final Map<SQLDialect, Class<? extends CreateTableImpl>> createTableImplMap;
    private final Map<SQLDialect, Class<? extends DropTableImpl>> dropTableImplMap;
    private final Map<SQLDialect, Class<? extends InsertImpl>> insertImplMap;

    public DialectStatementMap() {
        this.createTableImplMap = initSelectImplMap();
        this.dropTableImplMap = initDropImplMap();
        this.insertImplMap = initInsertImplMap();
    }

    public Class<? extends CreateTableImpl> getCreateTableImpl(SQLDialect dialect) {
        return createTableImplMap.get(dialect);
    }

    public Class<? extends DropTableImpl> getDropTableImpl(SQLDialect dialect) {
        return dropTableImplMap.get(dialect);
    }

    public Class<? extends InsertImpl> getInsertIntoImpl(SQLDialect dialect) {
        return insertImplMap.get(dialect);
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

    private Map<SQLDialect, Class<? extends InsertImpl>> initInsertImplMap() {
        return Map.ofEntries(
                Map.entry(SQLDialect.H2, H2InsertImpl.class)
        );
    }
}
