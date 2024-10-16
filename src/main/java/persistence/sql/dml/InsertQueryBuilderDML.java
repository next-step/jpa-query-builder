package persistence.sql.dml;

import persistence.sql.meta.ColumnField;
import persistence.sql.meta.ColumnFields;
import persistence.sql.meta.TableInfo;
import persistence.sql.sample.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilderDML {
    private static InsertQueryBuilderDML queryBuilderDML = new InsertQueryBuilderDML();
    private InsertQueryBuilderDML() { }
    public static InsertQueryBuilderDML getInstance() {
        return queryBuilderDML;
    }

    public String getQuery(Object object) throws Exception {
        TableInfo tableInfo = new TableInfo(object.getClass());
        String insertFormat = "insert into %s (%s) values (%s);";
        return String.format(insertFormat, tableInfo.getTableName(), columnsClause(object.getClass()), valueClause(object));
    }

    private String columnsClause(Class<?> clazz) {
        ColumnFields columnInfos = new ColumnFields(clazz);
        return columnInfos.getColumnFields()
                .stream().map(ColumnField::getName)
                .collect(Collectors.joining(", "));
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
