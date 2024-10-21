package persistence.sql.ddl;

import domain.Person;

import org.jetbrains.annotations.NotNull;
import persistence.sql.TableColumn;
import persistence.sql.TableId;

import java.util.List;
import java.util.stream.Collectors;


public class CreateTableQueryBuilder extends DDLQueryBuilder {

    private static final String CREATE_TABLE = "CREATE TABLE ";

    public CreateTableQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    @Override
    public String executeQuery() {
        return createTable();
    }

    private String createTable() {
        List<TableColumn> tableColumn = tableMeta.getTableColumn();
        TableId tableId = tableMeta.getTableId();
        String idColumn = createIdColumn(tableId);
        String columns = createColumns(tableColumn);
        return CREATE_TABLE + tableMeta.getTableName() + " ("+ idColumn + columns + ");";
    }

    private static String createColumns(List<TableColumn> tableColumn) {
        return tableColumn.stream().map(column -> column.columnName() + " " +
                H2DBDataType.castType(column.type()) +
                (column.isNotNullable() ? " NOT NULL" : "")).collect(Collectors.joining(", "));
    }

    @NotNull
    private static String createIdColumn(TableId tableId) {
        return tableId.getName() + " " + H2DBDataType.castType(tableId.getType()) + " PRIMARY KEY" + (tableId.isAutoIncrement() ? " AUTO_INCREMENT, " : ", ");
    }
}
