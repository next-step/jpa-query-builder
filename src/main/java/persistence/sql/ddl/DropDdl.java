package persistence.sql.ddl;

import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

public class DropDdl implements QueryBuilder {

    private static final String DROP_TABLE = "drop table ";

    @Override
    public String generate(Class<?> clazz, Database database) {
        TableColumn tableColumn = TableColumn.from(clazz);
        return DROP_TABLE + tableColumn.getName();
    }
}
