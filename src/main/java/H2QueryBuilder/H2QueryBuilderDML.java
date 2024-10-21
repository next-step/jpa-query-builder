package H2QueryBuilder;

import common.TableUtil;
import repository.QueryBuilderDML;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H2QueryBuilderDML implements QueryBuilderDML {
    private final static String INSERT_QUERY = "insert into %s (%s) values (%s);";

    @Override
    public String insert(Object object) {
        return generateInsertTableQuery(object);
    }

    private String generateInsertTableQuery(Object object) {
        String columnNames = columnsClause(object);
        String fieldValues = valuesClause(object);


        return String.format(INSERT_QUERY, new TableUtil(object.getClass()).getName(), columnNames, fieldValues);
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
