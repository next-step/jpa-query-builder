package persistence.sql.dml;

import persistence.common.EntityClazz;
import persistence.common.FieldValue;
import persistence.common.FieldValueList;

import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    public String getQuery(Object obj) {
        Class<?> clazz = obj.getClass();
        EntityClazz entityClazz = new EntityClazz(clazz);
        String insertQuery = "INSERT INTO " + entityClazz.getName() + " ";

        List<FieldValue> fieldValueList = new FieldValueList(obj).getFieldValueList();
        insertQuery += getColumnClause(fieldValueList);
        insertQuery += getValueClause(fieldValueList);
        insertQuery += ";";
        return insertQuery;
    }

    private String getValueClause(List<FieldValue> fieldValueList) {
        String values = fieldValueList.stream()
                .map(fv -> {
                    if (fv.getClazz().equals(String.class)) {
                        return "'" + fv.getValue() + "'";
                    }

                    return fv.getValue();
                }).collect(Collectors.joining(","));

        return "values (" + values + ") ";
    }

    private String getColumnClause(List<FieldValue> fieldValueList) {
        String fieldNames = fieldValueList.stream()
                .map(fv -> fv.getFieldName())
                .collect(Collectors.joining(","));

        return "(" + fieldNames + ") ";
    }
}
