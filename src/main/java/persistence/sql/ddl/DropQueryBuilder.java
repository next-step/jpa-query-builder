package persistence.sql.ddl;

import persistence.sql.domain.Table;
import persistence.sql.domain.dialect.Dialect;

public class DropQueryBuilder {
    private final Dialect dialect;

    public DropQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String build(Class<?> target) {
        Table table = Table.from(target);
        return String.format(dialect.getDropQueryTemplate(), table.getName());
    }
}
