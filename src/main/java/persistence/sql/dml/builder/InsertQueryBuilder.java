package persistence.sql.dml.builder;

import persistence.sql.dml.clause.Insert;
import persistence.sql.dml.clause.ValuesClause;

public class InsertQueryBuilder {
    public String generateSQL(Object object) {
        Insert insert = new Insert(object.getClass());
        return String.format("insert into %s (%s) values (%s)",
                insert.getTableName(),
                insert.getColumns(),
                new ValuesClause(object).getValues()
        );
    }
}
