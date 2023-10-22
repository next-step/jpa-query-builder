package persistence.sql.dml;

import persistence.sql.meta.ColumnMeta;
import persistence.sql.util.StringConstant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ColumnValues {

    private final Map<ColumnMeta, String> elements;

    private ColumnValues(Map<ColumnMeta, String> elements) {
        this.elements = elements;
    }

    public static ColumnValues of(Object object) {
        return new ColumnValues(buildElements(object));
    }

    public static ColumnValues ofFilteredAutoGenType(Object object) {
        Map<ColumnMeta, String> elements = buildElements(object);
        elements.keySet().stream()
                .filter(ColumnMeta::isGenerationTypeIdentity)
                .forEach(elements::remove);
        return new ColumnValues(elements);
    }

    private static Map<ColumnMeta, String> buildElements(Object object) {
        Map<ColumnMeta, String> columnValueMap = new LinkedHashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            put(columnValueMap, object, field);
        }
        return columnValueMap;
    }

    private static void put(Map<ColumnMeta, String> insertKeyValueMap, Object object, Field field) {
        ColumnMeta columnMeta = ColumnMeta.of(field);
        if (columnMeta.isTransient()) {
            return;
        }
        insertKeyValueMap.put(columnMeta, getColumnValue(object, field));
    }

    private static String getColumnValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return parseColumnValue(field.get(object));
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("데이터 처리가 불가능한 속성입니다.");
        }
    }

    private static String parseColumnValue(Object fieldValue) {
        if (fieldValue == null) {
            return StringConstant.NULL;
        }
        if (fieldValue.getClass() == String.class) {
            return StringConstant.SINGLE_QUOTATION + fieldValue + StringConstant.SINGLE_QUOTATION;
        }
        return fieldValue.toString();
    }

    public List<String> columns() {
        return elements.keySet().stream()
                .map(ColumnMeta::getColumnName)
                .collect(Collectors.toList());
    }

    public List<String> values() {
        return new ArrayList<>(elements.values());
    }

}
