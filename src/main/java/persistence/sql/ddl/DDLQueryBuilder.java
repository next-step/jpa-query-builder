package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.Table;

public class DDLQueryBuilder {

    private final Dialect dialect;
    private final Table table;

    public DDLQueryBuilder(Table table, Dialect dialect) {
        this.table = table;
        this.dialect = dialect;
    }

    public String buildCreateQuery() {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(table, dialect);
        return createQueryBuilder.build();
    }


    public String buildDropQuery() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(table);
        return dropQueryBuilder.build();
    }
}
