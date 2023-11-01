package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

public class DropQueryBuilder extends QueryBuilder {

    private final AnnotationBinder annotationBinder;
    private final MetadataGenerator metadataGenerator;

    public DropQueryBuilder(Dialect dialect) {
        super(dialect);
        this.annotationBinder = new AnnotationBinder(dialect);
        this.metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    }

    @Override
    public Query queryForObject(Object entity) {
        StringBuilder query = new StringBuilder();
        MetaData metaData = metadataGenerator.generator(entity.getClass());
        query.append("drop table ").append(metaData.getEntity());
        return new Query(query);
    }
}
