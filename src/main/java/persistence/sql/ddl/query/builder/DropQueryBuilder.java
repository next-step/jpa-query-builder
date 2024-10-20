package persistence.sql.ddl.query.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.TableName;

public class DropQueryBuilder {

    private static final String DROP_TABLE = "drop table if exists";

    private final Dialect dialect;
    private final StringBuilder queryString;

    private DropQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.queryString = new StringBuilder();
    }

    public static DropQueryBuilder builder(Dialect dialect) {
        return new DropQueryBuilder(dialect);
    }

    public String build() {
        return queryString.toString();
    }

    public DropQueryBuilder drop(TableName tableName) {
        queryString.append( DROP_TABLE )
                .append( " " )
                .append( tableName.value() );
        return this;
    }

}
