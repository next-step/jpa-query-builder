package persistence.sql.dml.assembler;

import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DataManipulationLanguageGenerator;
import persistence.sql.dml.insert.InsertQuery;

public class DataManipulationLanguageAssembler {
    private final H2Dialect dialect;
    private final DataManipulationLanguageGenerator dataManipulationLanguageGenerator;

    public DataManipulationLanguageAssembler(H2Dialect dialect, DataManipulationLanguageGenerator dataManipulationLanguageGenerator) {
        this.dialect = dialect;
        this.dataManipulationLanguageGenerator = dataManipulationLanguageGenerator;
    }

    public String generateInsert(InsertQuery insertQuery) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into");
        sb.append(insertQuery.getTableName(Person.class));
        sb.append('(');
        sb.append(')');
        sb.append(" values ");
        sb.append('(');

        return sb.toString();
    }

}
