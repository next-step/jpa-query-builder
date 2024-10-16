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
        ColumnFields columnInfos = new ColumnFields(clazz);
        return columnInfos.getColumnFields()
                .stream().map(ColumnField::getName)
                .collect(Collectors.joining(", "));
    }

    private String whereClause(String selectQuery, Object object) throws Exception {
        String whereQuery = "%s where %s";
        List<ColumnField> primaryFields = new ColumnFields(object.getClass())
                .getColumnFields().stream().filter(ColumnField::isPrimaryKey).collect(Collectors.toList());
        List<String> whereClauses = new ArrayList<>();

        for (ColumnField primaryField : primaryFields) {
            whereClauses.add(primaryField.generateWhereClause(object));
        }

        return String.format(whereQuery, selectQuery, whereClauses.stream().collect(Collectors.joining(" and ")));
    }

    private String valueClause(Object object) throws Exception {
        ColumnFields columnInfos = new ColumnFields(object.getClass());
        List<Field> fields = columnInfos.getColumnFields().stream()
                .filter(ColumnField::isNotTransient)
                .map(ColumnField::getField)
                .collect(Collectors.toList());

        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            values.add(String.valueOf(field.get(object)));
        }
        return String.join(", ", values);
    }

}
