package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.constant.BasicColumnType;

public abstract class Dialect {

    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE";

    public abstract String getTypeName(BasicColumnType columnType);

    public abstract String getGenerationStrategy(GenerationType generationType);

    public String getCreateTableCommand() {
        return CREATE_TABLE_COMMAND;
    }

}
