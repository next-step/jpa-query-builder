package persistence.sql.dml.impl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.QueryBuilder;
import persistence.sql.data.QueryType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder implements QueryBuilder {
    private final NameConverter nameConverter;

    public InsertQueryBuilder(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }

    @Override
    public QueryType queryType() {
        return QueryType.INSERT;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.INSERT.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Object value) {
        String tableName = loader.getTableName();
        List<Field> existValueFields = getExistValueFields(loader, value);

        String columns = existValueFields.stream()
                .map(loader::getColumnName)
                .map(nameConverter::convert)
                .collect(Collectors.joining(DELIMITER));

        String values = existValueFields.stream().map(field -> {
            Object columnValue = extractValue(field, value);
            return toColumnValue(columnValue);
        }).collect(Collectors.joining(DELIMITER));

        return "INSERT INTO %s (%s) VALUES (%s)".formatted(tableName, columns, values);
    }

    private List<Field> getExistValueFields(MetadataLoader<?> loader, Object value) {
        List<Field> fields = loader.getFieldAllByPredicate(field ->
                !field.isAnnotationPresent(Id.class) && !field.isAnnotationPresent(Transient.class));

        return fields.stream().filter(field -> extractValue(field, value) != null).toList();
    }
}
