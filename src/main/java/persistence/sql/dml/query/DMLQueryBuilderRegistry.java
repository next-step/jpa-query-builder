package persistence.sql.dml.query;

import static persistence.sql.dml.query.DMLType.DELETE;
import static persistence.sql.dml.query.DMLType.INSERT;
import static persistence.sql.dml.query.DMLType.SELECT;

import java.util.HashMap;
import java.util.Map;
import persistence.sql.ddl.exception.NotExistException;

public class DMLQueryBuilderRegistry {

    private final Map<DMLType, DMLQueryBuilder> builders;

    private DMLQueryBuilderRegistry() {
        this.builders = new HashMap<>();
        this.builders.put(INSERT, new InsertQueryBuilder());
        this.builders.put(SELECT, new SelectQueryBuilder());
        this.builders.put(DELETE, new DeleteQueryBuilder());
    }

    public static DMLQueryBuilder getQueryBuilder(DMLType type) {
        DMLQueryBuilder builder = LazyHolder.INSTANCE.builders.get(type);
        if (builder == null) {
            throw new NotExistException("DMLQueryBuilder mapped to " + type);
        }
        return LazyHolder.INSTANCE.builders.get(type);
    }

    private static class LazyHolder {
        private static final DMLQueryBuilderRegistry INSTANCE = new DMLQueryBuilderRegistry();
    }

}
