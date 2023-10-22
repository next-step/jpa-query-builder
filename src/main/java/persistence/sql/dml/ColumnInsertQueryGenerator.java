package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableFieldUtil;
import persistence.sql.TableQueryUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ColumnInsertQueryGenerator extends QueryBuilder {
    public ColumnInsertQueryGenerator(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) throws IllegalAccessException {

        return "INSERT INTO " +
            TableQueryUtil.getTableName(object.getClass()) +
            " " +
            columnsClause(object) +
            " VALUES " +
            valueClause(object) +
            ";";
    }

    private String columnsClause(Object object) throws IllegalAccessException {
        return "(" +
            Arrays
                .stream(getAvailableFields(object))
                .map(TableFieldUtil::getColumnName)
                .map(TableFieldUtil::replaceNameByBacktick)
                .collect(Collectors.joining(", ")) +
            ")";
    }

    private String valueClause(Object object) throws IllegalAccessException {
        ArrayList<String> keywords = new ArrayList<>();
        for (Field field : getAvailableFields(object)) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!column.nullable() && value == null) {
                    throw new RuntimeException(TableFieldUtil.getColumnName(field) + " is not nullable!!");
                }
            }

            if (value == null) {
                keywords.add("NULL");
            }
            else if (value instanceof String) {
                keywords.add(TableFieldUtil.replaceNameBySingleQuote(value.toString()));
            } else {
                keywords.add(value.toString());
            }
        }

        return "(" + String.join(", ", keywords) + ")";
    }

    private Field[] getAvailableFields(Object object) throws IllegalAccessException {
        ArrayList<Field> list = new ArrayList<>();
        for (Field field : TableFieldUtil.getAvailableFields(object.getClass())) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null) {
                    continue;
                }
            }

            list.add(field);
        }

        return list.toArray(new Field[0]);
    }
}
