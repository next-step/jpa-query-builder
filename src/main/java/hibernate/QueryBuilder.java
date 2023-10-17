package hibernate;

import domain.Person;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "create table %s (%s);";
    private static final String CREATE_COLUMN_QUERY = "%s %s";
    private static final String CREATE_COLUMN_QUERY_DELIMITER = ", ";

    public QueryBuilder() {
    }

    public String generateCreateQueries() {
        Class<Person> personClass = Person.class;
        String className = personClass.getSimpleName();
        String columns = fieldsToQueryColumn(personClass.getDeclaredFields());
        return String.format(CREATE_TABLE_QUERY, className, columns);
    }

    private String fieldsToQueryColumn(Field[] fields) {
        return Arrays.stream(fields)
                .map(this::fieldToQueryColumn)
                .collect(Collectors.joining(CREATE_COLUMN_QUERY_DELIMITER));
    }

    private String fieldToQueryColumn(final Field field) {
        String columnName = field.getName();
        String columnType = ColumnType.valueOf(field.getType())
                .getH2ColumnType();
        return String.format(CREATE_COLUMN_QUERY, columnName, columnType);
    }
}
