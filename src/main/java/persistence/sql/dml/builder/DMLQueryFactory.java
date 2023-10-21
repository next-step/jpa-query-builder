package persistence.sql.dml.builder;

import persistence.sql.dml.model.DMLType;

import java.util.HashMap;
import java.util.Map;

public class DMLQueryFactory {
    private static final Map<DMLType, DMLQueryBuilder> BUILDER_BY_DML_TYPE = new HashMap<>();

    static {
        BUILDER_BY_DML_TYPE.put(DMLType.INSERT, new InsertDMLQueryBuilder());
    }

    public static DMLQueryBuilder createQueryBuilder(DMLType dmlType) {
        return BUILDER_BY_DML_TYPE.get(dmlType);
    }
}
