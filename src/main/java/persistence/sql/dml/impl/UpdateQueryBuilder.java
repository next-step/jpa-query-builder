package persistence.sql.dml.impl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.QueryBuilder;
import persistence.sql.data.QueryType;

import java.lang.reflect.Field;
import java.util.List;

public class UpdateQueryBuilder implements QueryBuilder {
    private final NameConverter nameConverter;

    public UpdateQueryBuilder(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }

    @Override
    public QueryType queryType() {
        return QueryType.UPDATE;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.UPDATE.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Object value) {
        String tableName = loader.getTableName();
        String setClause = getSetClause(loader, value);
        String whereClause = getWhereIdClause(loader, value);

        return "UPDATE %s SET %s WHERE %s".formatted(tableName, setClause, whereClause);
    }

    private String getSetClause(MetadataLoader<?> loader, Object value) {
        StringBuilder sb = new StringBuilder();
        List<Field> fields = loader.getFieldAllByPredicate(field -> !field.isAnnotationPresent(Id.class) && !field.isAnnotationPresent(Transient.class));

        for (Field field : fields) {
            String columnName = loader.getColumnName(field);
            Object columnValue = extractValue(field, value);

            sb.append("%s = %s, ".formatted(nameConverter.convert(columnName), toColumnValue(columnValue)));
        }

        return sb.substring(0, sb.length() - 2);
    }
}
