package persistence.sql.dml.query;

import static persistence.sql.dml.query.DMLType.INSERT;
import static persistence.sql.dml.query.DMLType.SELECT;

import java.util.HashMap;
import java.util.Map;

public class DMLQueryBuilderRegistry {

    private final Map<DMLType, DMLQueryBuilder> builders;

    private DMLQueryBuilderRegistry() {
        this.builders = new HashMap<>();
        this.builders.put(INSERT, new InsertQueryBuilder());
        this.builders.put(SELECT, new SelectQueryBuilder());
    }

    public static DMLQueryBuilder getQueryBuilder(DMLType type) {
        return LazyHolder.INSTANCE.builders.get(type);
    }

    private static class LazyHolder {
        private static final DMLQueryBuilderRegistry INSTANCE = new DMLQueryBuilderRegistry();
    }

}
