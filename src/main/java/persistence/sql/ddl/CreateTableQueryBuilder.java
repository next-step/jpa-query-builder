package persistence.sql.ddl;

import domain.Person;
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
        String idColumn = tableId.getName() + " " + H2DBDataType.castType(tableId.getType()) + " PRIMARY KEY" + (tableId.isAutoIncrement() ? " AUTO_INCREMENT, " : ", ");
        String columns = tableColumn.stream().map(column -> {
            StringBuilder sb = new StringBuilder();
            sb.append(column.getColumnName()).append(" ");
            sb.append(H2DBDataType.castType(column.getType()));
            sb.append(column.isNotNullable() ? " NOT NULL" : "");
            return sb.toString();
        }).collect(Collectors.joining(", "));

        return CREATE_TABLE + tableMeta.getTableName() + " ("+ idColumn + columns + ");";
    }

    public static void main(String[] args) {
        CreateTableQueryBuilder createTableQueryBuilder = new CreateTableQueryBuilder(Person.class);
        System.out.println(createTableQueryBuilder.executeQuery());
    }
}
