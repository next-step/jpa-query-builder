package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.dialect.Dialect;

public class DdlGenerator {

    private final QueryBuilder createQueryBuilder;
    private final QueryBuilder dropQueryBuilder;

    private DdlGenerator(Dialect dialect) {
        this.createQueryBuilder = CreateQueryBuilder.from(dialect);
        this.dropQueryBuilder = DropQueryBuilder.from();
    }

    public static DdlGenerator from(Dialect dialect) {
        return new DdlGenerator(dialect);
    }

    public String generateCreateQuery(Class<?> clazz) {
        return createQueryBuilder.generateQuery(clazz);
    }

    public String generateDropQuery(Class<?> clazz) {
        return dropQueryBuilder.generateQuery(clazz);
    }
}
