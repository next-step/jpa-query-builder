package persistence.sql.ddl.table;

import jakarta.persistence.Id;
import persistence.sql.ddl.column.MySqlColumn;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MySqlTable implements Table {

    private final TableName name;
    private final List<MySqlColumn> columns;
    private final Field idColumn;

    private MySqlTable(TableName name, List<MySqlColumn> columns, Field idColumn) {
        this.name = name;
        this.columns = columns;
        this.idColumn = idColumn;
    }

    public static MySqlTable from(Class<?> entity) {
        TableName name = TableName.from(entity);

        List<MySqlColumn> columns = Arrays.stream(entity.getDeclaredFields())
                .map(MySqlColumn::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Field idColumn = findIdColumn(entity);

        return new MySqlTable(name, columns, idColumn);
    }

    private static Field findIdColumn(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Persistent entity '%s' should have primary key", entity.getName())));
    }

    @Override
    public String createTable() {
        return String.format("CREATE TABLE %s (%s, %s);", name.getName(), getColumnsDefinition(), getIdColumnDefinition());
    }

    @Override
    public String dropTable() {
        return String.format("DROP TABLE %s;", name.getName());
    }

    private String getColumnsDefinition() {
        return columns.stream()
                .map(MySqlColumn::defineColumn)
                .collect(Collectors.joining(", "));
    }

    public String getIdColumnDefinition() {
        return String.format("PRIMARY KEY(%s)", idColumn.getName());
    }
}
