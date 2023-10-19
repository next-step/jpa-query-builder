package persistence.sql.ddl.assembler;

import persistence.sql.ddl.DataDefinitionLanguageGenerator;
import persistence.sql.ddl.TableCreator;
import persistence.sql.ddl.vo.DatabaseField;

public class DataDefinitionLanguageAssembler {
    private final DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator;

    public DataDefinitionLanguageAssembler(DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator) {
        this.dataDefinitionLanguageGenerator = dataDefinitionLanguageGenerator;
    }

    public String assembleCreateTableQuery(Class<?> cls) {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(cls);
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableCreator.getTableName());
        sb.append('(');
        sb.append(System.lineSeparator());
        int fieldSize = tableCreator.getFields().getDatabaseFields().size();
        for (int i = 0; i < fieldSize; i++) {
            DatabaseField databaseField = tableCreator.getFields().getDatabaseFields().get(i);
            sb.append(databaseField);
            if (i != fieldSize - 1) {
                sb.append(',');
            }
            sb.append(System.lineSeparator());
        }
        sb.append(')');
        return sb.toString();
    }

}
