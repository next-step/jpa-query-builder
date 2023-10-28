package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

public class SelectQueryBuilder extends QueryBuilder {

    private AnnotationBinder annotationBinder = new AnnotationBinder();
    private MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);

    public SelectQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query findAll(Class<?> domain) {
        StringBuilder sb = new StringBuilder();

        StringBuilder queryStringBuilder = sb.append("select * from ")
                .append(getMetaData(domain).getEntity());
        return new Query(queryStringBuilder);
    }

    private MetaData getMetaData(Class<?> domain) {
        return metadataGenerator.generator(domain);
    }
}
