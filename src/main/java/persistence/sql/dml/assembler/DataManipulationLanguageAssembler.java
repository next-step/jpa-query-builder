package persistence.sql.dml.assembler;

import persistence.sql.dialect.Dialect;
import persistence.sql.dml.DataManipulationLanguageGenerator;
import persistence.sql.dml.delete.DeleteQuery;
import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.dml.select.SelectQuery;
import persistence.sql.dml.where.WhereQuery;

public class DataManipulationLanguageAssembler {
    private final Dialect dialect;
    private final DataManipulationLanguageGenerator dataManipulationLanguageGenerator;

    public DataManipulationLanguageAssembler(Dialect dialect, DataManipulationLanguageGenerator dataManipulationLanguageGenerator) {
        this.dialect = dialect;
        this.dataManipulationLanguageGenerator = dataManipulationLanguageGenerator;
    }

    public String generateInsert(Object object) {
        InsertQuery insertQuery = dataManipulationLanguageGenerator.buildInsertQuery(object);
        return dialect.insertBuilder(insertQuery);
    }

    public String generateSelect(Class<?> cls) {
        SelectQuery selectQuery = dataManipulationLanguageGenerator.buildSelectQuery(cls);
        return dialect.selectBuilder(selectQuery);
    }

    public String generateSelectWithWhere(Class<?> cls, long id) {
        SelectQuery selectQuery = dataManipulationLanguageGenerator.buildSelectQuery(cls);
        WhereQuery whereQuery = dataManipulationLanguageGenerator.buildWhereQuery(id);
        return dialect.selectConditionBuilder(selectQuery, whereQuery);
    }

    public String generateDeleteWithWhere(Class<?> cls, long id) {
        DeleteQuery deleteQuery = dataManipulationLanguageGenerator.buildDeleteQuery(cls);
        WhereQuery whereQuery = dataManipulationLanguageGenerator.buildWhereQuery(id);
        return dialect.deleteBuilder(deleteQuery, whereQuery);
    }
}
