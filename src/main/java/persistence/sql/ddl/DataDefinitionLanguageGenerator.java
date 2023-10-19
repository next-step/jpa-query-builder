package persistence.sql.ddl;

public class DataDefinitionLanguageGenerator {
    private final GetTableNameFromClass getTableNameFromClass;
    private final GetFieldFromClass getFieldFromClass;


    public DataDefinitionLanguageGenerator(GetTableNameFromClass getTableNameFromClass,
                                           GetFieldFromClass getFieldFromClass) {
        this.getTableNameFromClass = getTableNameFromClass;
        this.getFieldFromClass = getFieldFromClass;
    }

    public TableCreator generateTableCreatorWithClass(Class<?> dbClass) {
        return TableCreator.builder()
                           .tableName(getTableNameFromClass.execute(dbClass))
                           .fields(getFieldFromClass.execute(dbClass))
                           .build();
    }

    public TableRemover generateTableRemoverWithClass(Class<?> dbClass) {
        return TableRemover.builder().tableName(getTableNameFromClass.execute(dbClass)).build();
    }
}
