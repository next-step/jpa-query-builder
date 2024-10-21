package H2QueryBuilder;

import repository.QueryBuilderDML;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class H2QueryBuilderDML implements QueryBuilderDML {
    private final static String INSERT_QUERY = "insert into %s (%s) values (%s);";
    private final static String FIND_ALL_QUERY = "select %s from %s;";
    private final static String FIND_BY_ID_QUERY = "select %s from %s where %s;";
    private final static String DELETE_QUERY = "delete from %s where %s;";

    @Override
    public String insert(Object object) {
        return generateInsertTableQuery(object);
    }

    @Override
    public String findAll(Object object) {
        return String.format(FIND_ALL_QUERY, this.generateSelectTableQuery(object), new TableName(object.getClass()).getName());
    }

    @Override
    public String findById(Object object) {
        return String.format(FIND_BY_ID_QUERY, generateSelectTableQuery(object), new TableName(object.getClass()).getName(), whereClauseForId(object));
    }

    @Override
    public String delete(Object object) {
        return String.format(DELETE_QUERY, new TableName(object.getClass()).getName(), whereClause(object));
    }

    private String whereClauseForId(Object object) {
        return this.getColumn(object.getClass(), object).stream()
                .map(tableColumnAttribute -> {
                    if (tableColumnAttribute.getColumnName().equals("id")) {
                        return String.format("%s = %s", tableColumnAttribute.getColumnName(), tableColumnAttribute.getFieldValue());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" and "));
    }

    private String whereClause(Object object) {
        return this.getColumn(object.getClass(), object).stream()
                .filter(tableColumnAttribute -> !tableColumnAttribute.isTransient())
                .map(tableColumnAttribute -> String.format("%s = %s", tableColumnAttribute.getColumnName(), tableColumnAttribute.getFieldValue()))
                .collect(Collectors.joining(" and "));
    }

    private String generateSelectTableQuery(Object object) {
        return this.getColumn(object.getClass(), object).stream()
                .filter(tableColumnAttribute -> !tableColumnAttribute.isTransient())
                .map(TableColumnAttribute::getColumnName)
                .collect(Collectors.joining(", "));
    }

    private String generateInsertTableQuery(Object object) {
        String columnNames = columnsClause(object);
        String fieldValues = valuesClause(object);


        return String.format(INSERT_QUERY, new TableName(object.getClass()).getName(), columnNames, fieldValues);
    }

    private String valuesClause(Object object) {
        return this.getColumn(object.getClass(), object)
                .stream()
                .filter(tableColumnAttribute -> !tableColumnAttribute.isTransient())
                .filter(tableColumnAttribute -> !tableColumnAttribute.isAutoIncrement())
                .map(TableColumnAttribute::getFieldValue)
                .collect(Collectors.joining(", "));
    }

    private String columnsClause(Object object) {
        return this.getColumn(object.getClass(), object)
                .stream()
                .filter(tableColumnAttribute -> !tableColumnAttribute.isTransient())
                .filter(tableColumnAttribute -> !tableColumnAttribute.isAutoIncrement())
                .map(TableColumnAttribute::getColumnName)
                .collect(Collectors.joining(", "));
    }

    private List<TableColumnAttribute> getColumn(Class<?> entityClass, Object object) {
        TableColumnAttribute tableColumnAttribute = new TableColumnAttribute();

        Field[] fields = entityClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                tableColumnAttribute.generateTableColumnMeta(field, object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return tableColumnAttribute.getTableAttributes();
    }
}
