package persistence.sql.dialect;

public abstract class Dialect {

    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE";

    public abstract String convertDataType(Class<?> clazz);

    public String getCreateTableCommand() {
        return CREATE_TABLE_COMMAND;
    }

}
