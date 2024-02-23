package persistence.sql.dml.builder;

import persistence.sql.dml.caluse.ColumnsClause;
import persistence.sql.dml.caluse.ValuesClause;
import persistence.sql.meta.table.Table;

public class InsertQueryBuilder {
    public String generateSQL(Object object) {
        return String.format("insert into %s (%s) values (%s)",
                new Table(object.getClass()).getTableName(),
                new ColumnsClause(object.getClass()).getColumns(),
                new ValuesClause(object).getValues()
        );
    }
}
