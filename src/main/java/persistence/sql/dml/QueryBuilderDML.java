package persistence.sql.dml;

import persistence.sql.meta.ColumnField;
import persistence.sql.meta.ColumnFields;
import persistence.sql.meta.TableInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilderDML {
    private static QueryBuilderDML queryBuilderDML = new QueryBuilderDML();
    private QueryBuilderDML() { }
    public static QueryBuilderDML getInstance() {
        return queryBuilderDML;
    }

    public String insert(Object object) throws Exception {
        TableInfo tableInfo = new TableInfo(object.getClass());
        String insertFormat = "insert into %s (%s) values (%s)";
        return String.format(insertFormat, tableInfo.getTableName(), columnsClause(object.getClass()), valueClause(object));
    }

    public String findAll(Object object) {
        TableInfo tableInfo = new TableInfo(object.getClass());
        String findAllFormat = "select %s from %s";
        return String.format(findAllFormat,columnsClause(object.getClass()), tableInfo.getTableName());
    }

    public String findById(Object object) throws Exception {
        TableInfo tableInfo = new TableInfo(object.getClass());
        String findAllFormat = "select %s from %s";
        return whereClause(String.format(findAllFormat, columnsClause(object.getClass()), tableInfo.getTableName()), object);
    }

    public String deleteById(Object object) throws Exception {
        TableInfo tableInfo = new TableInfo(object.getClass());
        String findAllFormat = "delete from %s";
        return whereClause(String.format(findAllFormat, tableInfo.getTableName()), object);
    }

    private String columnsClause(Class<?> clazz) {
        return new ColumnFields(clazz).getColumnClause();
    }

    private String valueClause(Object object) {
        return new ColumnFields(object.getClass()).valueClause(object);
    }

    private String whereClause(String selectQuery, Object object) {
        String whereQuery = "%s where %s";
        List<ColumnField> primaryFields = new ColumnFields(object.getClass()).getPrimary();

        String primaryWhereClause = primaryFields.stream().map(columnField -> {
            try {
                return columnField.generateWhereClause(object);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(" and "));

        return String.format(whereQuery, selectQuery, primaryWhereClause);
    }

}
