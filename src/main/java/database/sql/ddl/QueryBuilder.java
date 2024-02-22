package database.sql.ddl;

import database.sql.util.type.MySQLTypeConverter;
import database.sql.util.type.TypeConverter;

public class QueryBuilder {
    private static final QueryBuilder INSTANCE = new QueryBuilder();
    private static final TypeConverter typeConverter = new MySQLTypeConverter();

    private QueryBuilder() {
    }

    public static QueryBuilder getInstance() {
        return INSTANCE;
    }

    public String buildCreateQuery(Class<?> entityClass) {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityClass, typeConverter);
        return createQueryBuilder.buildQuery();
    }

    public String buildDeleteQuery(Class<?> entityClass) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(entityClass);
        return dropQueryBuilder.buildQuery();
    }
}
