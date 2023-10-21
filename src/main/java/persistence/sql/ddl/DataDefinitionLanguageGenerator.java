package persistence.sql.ddl;

import persistence.sql.ddl.usecase.GetFieldFromClassUseCase;
import persistence.sql.ddl.usecase.GetTableNameFromClassUseCase;

public class DataDefinitionLanguageGenerator {
    private final GetTableNameFromClassUseCase getTableNameFromClassUseCase;
    private final GetFieldFromClassUseCase getFieldFromClass;


    public DataDefinitionLanguageGenerator(GetTableNameFromClassUseCase getTableNameFromClassUseCase,
                                           GetFieldFromClassUseCase getFieldFromClass) {
        this.getTableNameFromClassUseCase = getTableNameFromClassUseCase;
        this.getFieldFromClass = getFieldFromClass;
    }

    public TableCreator generateTableCreatorWithClass(Class<?> dbClass) {
        return TableCreator.builder()
                           .tableName(getTableNameFromClassUseCase.execute(dbClass))
                           .fields(getFieldFromClass.execute(dbClass))
                           .build();
    }

    public TableRemover generateTableRemoverWithClass(Class<?> dbClass) {
        return TableRemover.builder().tableName(getTableNameFromClassUseCase.execute(dbClass)).build();
    }
}
