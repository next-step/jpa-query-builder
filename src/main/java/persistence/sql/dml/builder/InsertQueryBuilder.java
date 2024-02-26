package persistence.sql.dml.builder;

import persistence.sql.dml.clause.Insert;
import persistence.sql.dml.clause.ValuesClause;
import persistence.sql.dml.factory.InsertFactory;

public class InsertQueryBuilder {
    public String generateSQL(Object object) {
        Insert insert = InsertFactory.getInsert(object.getClass());
        return String.format("insert into %s (%s) values (%s)",
                insert.getTableName(),
                insert.getColumns(),
                new ValuesClause(object).getValues()
        );
    }
}
