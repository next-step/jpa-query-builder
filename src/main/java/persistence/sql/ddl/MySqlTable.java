package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlTable implements Table {

    private static final String DOT = "\\.";

    private final String name;
    private final List<MySqlColumn> columns;
    private final Field idColumn;

    private MySqlTable(String name, List<MySqlColumn> columns, Field idColumn) {
        this.name = name;
        this.columns = columns;
        this.idColumn = idColumn;
    }

    public static MySqlTable from(Class<?> entity) {
        String name = findName(entity);

        List<MySqlColumn> columns = Arrays.stream(entity.getDeclaredFields())
                .map(MySqlColumn::from)
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

    private static String findName(Class<?> entity) {
        jakarta.persistence.Table table = entity.getAnnotation(jakarta.persistence.Table.class);

        if (table != null && !table.name().isBlank()) {
            return table.name();
        }

        String name = entity.getName();
        String[] splitByDotName = name.split(DOT);

        return splitByDotName[splitByDotName.length - 1].toLowerCase();
    }

    @Override
    public String createTable() {
        return "CREATE TABLE "
                + name
                + " ("
                + getColumnsDefinition()
                + getIdColumnDefinition();
    }

    private String getColumnsDefinition() {
        return columns.stream()
                .map(MySqlColumn::defineColumn)
                .collect(Collectors.joining(", "));
    }

    public String getIdColumnDefinition() {
        return String.format(", PRIMARY KEY(%s));", idColumn.getName());
    }
}
