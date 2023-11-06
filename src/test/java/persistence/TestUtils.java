package persistence;

import persistence.sql.ddl.DataDefinitionLanguageGenerator;
import persistence.sql.ddl.assembler.DataDefinitionLanguageAssembler;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DataManipulationLanguageGenerator;
import persistence.sql.dml.assembler.DataManipulationLanguageAssembler;
import persistence.sql.usecase.GetFieldFromClassUseCase;
import persistence.sql.usecase.GetFieldValueUseCase;
import persistence.sql.usecase.GetIdDatabaseFieldUseCase;
import persistence.sql.usecase.GetTableNameFromClassUseCase;

public class TestUtils {
    public static DataManipulationLanguageAssembler createDataManipulationLanguageAssembler() {
        H2Dialect h2Dialect = new H2Dialect();
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        GetFieldValueUseCase getFieldValueUseCase = new GetFieldValueUseCase();
        GetIdDatabaseFieldUseCase getIdDatabaseFieldUseCase = new GetIdDatabaseFieldUseCase(getFieldFromClassUseCase);
        DataManipulationLanguageGenerator dataManipulationLanguageGenerator = new DataManipulationLanguageGenerator(
            getTableNameFromClassUseCase,
            getFieldFromClassUseCase,
            getFieldValueUseCase,
            getIdDatabaseFieldUseCase);
        return new DataManipulationLanguageAssembler(
            h2Dialect, dataManipulationLanguageGenerator
        );
    }

    public static DataDefinitionLanguageAssembler createDataDefinitionLanguageAssembler() {
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator = new DataDefinitionLanguageGenerator(
            getTableNameFromClassUseCase, getFieldFromClassUseCase
        );
        return new DataDefinitionLanguageAssembler(dataDefinitionLanguageGenerator);
    }
}
