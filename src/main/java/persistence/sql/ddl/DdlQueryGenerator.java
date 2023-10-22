package persistence.sql.ddl;

import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.ddl.builder.DropQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.meta.EntityMeta;

public class DdlQueryGenerator {

    private final Dialect dialect;

    private DdlQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public static DdlQueryGenerator of(Dialect dialect) {
        return new DdlQueryGenerator(dialect);
    }

    public String generateCreateQuery(EntityMeta entityMeta) {
        return CreateQueryBuilder.of(dialect, entityMeta).build();
    }

    public String generateDropQuery(EntityMeta entityMeta) {
        return DropQueryBuilder.of(entityMeta).build();
    }

}
