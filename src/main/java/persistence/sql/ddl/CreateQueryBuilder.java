package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CreateQueryBuilder{
    private final static String CREATE_COMMAND = "CREATE TABLE %s";

    private final static String PRIMARY_CONSTRAINT = "PRIMARY KEY (%s)";

    private List<ColumnBuilder> columns;

    public CreateQueryBuilder(Class<?> clazz) {
        this.columns = getColumns(clazz);
    }

    public String getQuery(Class<?> clazz) {
        return new StringBuilder(format(CREATE_COMMAND, clazz.getSimpleName()))
                .append("( ")
                .append(getString(this.columns))
                .append(", ")
                .append(format(PRIMARY_CONSTRAINT, getPrimaryKey(this.columns).getName()))
                .append(" );").toString();
    }


    private String getString(List<ColumnBuilder> columns) {
        return columns.stream()
                .map(x->x.getName() + " " + x.getType())
                .collect(Collectors.joining(", ")).toString();
    }

    private List<ColumnBuilder> getColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(x -> new ColumnBuilder(x.getName(), x.getType(), x.isAnnotationPresent(Id.class)))
                .collect(Collectors.toList());
    }

    private ColumnBuilder getPrimaryKey(List<ColumnBuilder> columns) {
        return columns.stream().filter(ColumnBuilder::isPrimaryKey).findAny().get();
    }
}
