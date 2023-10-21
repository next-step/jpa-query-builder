package persistence.sql.dml;

import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityColumns;
import persistence.sql.entitymetadata.model.EntityTable;

public class InsertDMLQueryBuilder<E> extends DMLQueryBuilder<E> {
    public InsertDMLQueryBuilder(DbmsStrategy dbmsStrategy, E entity) {
        super(dbmsStrategy, entity);
    }

    @Override
    public String build() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                createTableNameDefinition(),
                createInsertColumnsClause(),
                createInsertValueClause());
    }

    /**
     * insert into table (column1, column2, column3) values (value1, value2, value3)
     */
    private String createInsertColumnsClause() {
       return "(" + String.join(", ", defaultColumnsClause) + ")";
    }

    private String createInsertValueClause() {
        return "(" + String.join(", ", defaultValuesClause) + ")";
    }
}
