package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.Table;

public class DDLQueryBuilder {

    private final Dialect dialect;

    public DDLQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildCreateQuery(Table table) {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(table, dialect);
        return createQueryBuilder.build();
    }


    public String buildDropQuery(Table table) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(table);
        return dropQueryBuilder.build();
    }
}
