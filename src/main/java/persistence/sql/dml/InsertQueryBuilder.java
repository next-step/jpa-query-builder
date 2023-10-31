package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder extends QueryBuilder {

    private AnnotationBinder annotationBinder = new AnnotationBinder();
    private MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);

    public InsertQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query queryForObject(Object domain) {
        StringBuilder sb = new StringBuilder();
        MetaData metaData = getMetaData(domain.getClass());
        //여기서 인서트문 만들어주기
        StringBuilder query = sb.append("insert into ")
                .append(metaData.getEntity()).append("(")
                .append(columnBuilder(metaData))
                .append(") values(")
                .append(valueBuilder(domain, metaData))
                .append(")");

        return new Query(query);
    }

    private MetaData getMetaData(Class<?> domain) {
        return metadataGenerator.generator(domain);
    }

    private String columnBuilder(MetaData metaData) {
        List<String> columns = metaData.getColumns()
                .stream()
                .map(ColumnMetaData::getName)
                .collect(Collectors.toList());
        return listToStringBuilder(columns);
    }
    private String listToStringBuilder(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        strings.forEach(s -> sb.append(s).append(", "));
        int index = sb.length() - 2;
        sb.deleteCharAt(index);
        return sb.toString();
    }

    private String valueBuilder(Object domain, MetaData metaData) {
        StringBuilder sb = new StringBuilder();
        List<String> values = metaData.getColumns()
                .stream()
                .map(column -> {
            try {
                Field field = domain.getClass().getDeclaredField(column.getFieldName());
                field.setAccessible(Boolean.TRUE);
                Object value = field.get(domain);
                return matchValueType(value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return listToStringBuilder(values);
    }

    private String matchValueType(Object value) {
        if(value instanceof String) {
            return "'"+value+"'";
        }
        return String.valueOf(value);
    }
}
