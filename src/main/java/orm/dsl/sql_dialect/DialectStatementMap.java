package orm.dsl.sql_dialect;

import orm.SQLDialect;
import orm.dsl.ddl.CreateTableImpl;
import orm.dsl.ddl.DropTableImpl;
import orm.dsl.dml.DeleteImpl;
import orm.dsl.dml.InsertImpl;
import orm.dsl.dml.SelectImpl;
import orm.dsl.sql_dialect.h2.*;

import java.util.Map;

public class DialectStatementMap {

    private final Map<SQLDialect, Class<? extends CreateTableImpl>> createTableImplMap;
    private final Map<SQLDialect, Class<? extends DropTableImpl>> dropTableImplMap;
    private final Map<SQLDialect, Class<? extends InsertImpl>> insertImplMap;
    private final Map<SQLDialect, Class<? extends SelectImpl>> selectImplMap;
    private final Map<SQLDialect, Class<? extends DeleteImpl>> deleteImplMap;

    public DialectStatementMap() {
        this.createTableImplMap = initCreateImplMap();
        this.dropTableImplMap = initDropImplMap();
        this.insertImplMap = initInsertImplMap();
        this.selectImplMap = initSelectImplMap();
        this.deleteImplMap = initDeleteImplMap();
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

    public Class<? extends SelectImpl> getSelectImpl(SQLDialect dialect) {
        return selectImplMap.get(dialect);
    }

    public Class<? extends DeleteImpl> getDeleteIntoImpl(SQLDialect dialect) {
        return deleteImplMap.get(dialect);
    }

    private Map<SQLDialect, Class<? extends CreateTableImpl>> initCreateImplMap() {
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

    private Map<SQLDialect, Class<? extends SelectImpl>> initSelectImplMap() {
        return Map.ofEntries(
                Map.entry(SQLDialect.H2, H2SelectImpl.class)
        );
    }

    private Map<SQLDialect, Class<? extends DeleteImpl>> initDeleteImplMap() {
        return Map.ofEntries(
                Map.entry(SQLDialect.H2, H2DeleteImpl.class)
        );
    }
}
