package persistence.sql.dml.builder;

import persistence.sql.dml.clause.DMLMeta;
import persistence.sql.dml.clause.ValuesClause;

public class InsertQueryBuilder {
    public String generateSQL(Object object) {
        DMLMeta table = new DMLMeta(object.getClass());
        return String.format("insert into %s (%s) values (%s)",
                table.getTableName(),
                table.getColumns(),
                new ValuesClause(object).getValues()
        );
    }
}
