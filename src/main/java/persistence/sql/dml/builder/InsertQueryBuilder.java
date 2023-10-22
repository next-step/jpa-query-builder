package persistence.sql.dml.builder;

import persistence.entitiy.attribute.GeneralAttribute;
import persistence.entitiy.attribute.id.IdAttribute;
import persistence.entitiy.context.EntityContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    public InsertQueryBuilder() {
    }

    public String prepareStatement(EntityContext entityContext) {
        String tableName = entityContext.getTableName();

        List<Map.Entry<GeneralAttribute, String>> attributeWithValues = getFilteredAttributeWithValues(entityContext);

        List<String> columnNames = extractColumnNames(attributeWithValues);
        List<String> values = formatValues(attributeWithValues);

        Map.Entry<IdAttribute, String> idAttributeWithValuePair = entityContext.getIdAttributeWithValuePair();
        addIdAttributeToColumnsAndValues(idAttributeWithValuePair, columnNames, values);

        return formatInsertStatement(tableName, columnNames, values);
    }

    private List<Map.Entry<GeneralAttribute, String>> getFilteredAttributeWithValues(EntityContext entityContext) {
        return entityContext.getAttributeWithValueMap()
                .entrySet().stream()
                .filter(entry -> {
                    boolean isNull = entry.getValue() == null;
                    if (!entry.getKey().isNullable() && isNull) {
                        throw new IllegalArgumentException(String.format("[%s] null 값이 들어올 수 없습니다.", entry.getKey()));
                    }
                    return entry.getValue() != null;
                })
                .collect(Collectors.toList());
    }

    private List<String> extractColumnNames(List<Map.Entry<GeneralAttribute, String>> attributeWithValues) {
        return attributeWithValues.stream()
                .map(Map.Entry::getKey)
                .map(GeneralAttribute::getColumnName)
                .collect(Collectors.toList());
    }

    private List<String> formatValues(List<Map.Entry<GeneralAttribute, String>> attributeWithValues) {
        return attributeWithValues.stream()
                .map(Map.Entry::getValue)
                .map(value -> String.format("'%s'", value))
                .collect(Collectors.toList());
    }

    private void addIdAttributeToColumnsAndValues(Map.Entry<IdAttribute, String> idAttributeWithValuePair,
                                                  List<String> columnNames, List<String> values) {
        IdAttribute idAttribute = idAttributeWithValuePair.getKey();
        String idValue = (idAttributeWithValuePair.getValue() == null ? null :
                String.format("'%s'", idAttributeWithValuePair.getValue()));

        if (idValue != null) {
            columnNames.add(0, idAttribute.getColumnName());
            values.add(0, idValue);
        }
    }

    private String formatInsertStatement(String tableName, List<String> columnNames, List<String> values) {
        return String.format("INSERT INTO %s( %s ) VALUES ( %s )", tableName,
                String.join(", ", columnNames), String.join(", ", values));
    }
}
