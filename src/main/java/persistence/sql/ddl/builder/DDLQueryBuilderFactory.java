package persistence.sql.ddl.builder;

import persistence.sql.common.DDLType;

import java.util.HashMap;
import java.util.Map;

public class DDLQueryBuilderFactory {
    private static final Map<DDLType, DDLQueryBuilder> BUILDER_BY_DDL_TYPE = new HashMap<>();

    static {
        BUILDER_BY_DDL_TYPE.put(DDLType.CREATE, new CreateDDLQueryBuilder());
        BUILDER_BY_DDL_TYPE.put(DDLType.DROP, new DropDDLQueryBuilder());
    }

    public static DDLQueryBuilder createQueryBuilder(DDLType ddlType) {
        return BUILDER_BY_DDL_TYPE.get(ddlType);
    }
}
