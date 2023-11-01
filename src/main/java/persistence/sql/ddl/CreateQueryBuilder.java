package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.*;

import java.util.List;

public class CreateQueryBuilder extends QueryBuilder {

    private final AnnotationBinder annotationBinder;
    private final MetadataGenerator metadataGenerator;

    public CreateQueryBuilder(Dialect dialect) {
        super(dialect);
        this.annotationBinder = new AnnotationBinder(dialect);
        this.metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    }

    @Override
    public Query queryForObject(Object entity) {
        StringBuilder query = new StringBuilder();
        MetaData metaData = metadataGenerator.generator(entity.getClass());
        query.append("create table ")
                .append(metaData.getEntity())
                .append(" (")
                .append(metaData.getId())
                .append(metaData.getIdOption())
                .append(columnTypeName(metaData.getColumns()))
                .append(" )");
        return new Query(query);
    }

    private String columnTypeName(List<ColumnMetaData> columns) {
        StringBuilder columnQuery = new StringBuilder();
        for (ColumnMetaData column : columns) {
            columnQuery.append(columnQueryBuilder(column));
        }
        return columnQuery.toString();
    }

    private String columnQueryBuilder(ColumnMetaData columnMetaData) {
        String query = ", "+ columnMetaData.getName() + " "+ dialect.javaTypeToJdbcType(columnMetaData.getType());

        if(columnMetaData.getType().equals("String")) {
            query = query + "(" +columnMetaData.getLength()+") ";
        }

        if(!columnMetaData.isNullable()) {
            query = query + " not null";
        }
        return query;
    }
}
