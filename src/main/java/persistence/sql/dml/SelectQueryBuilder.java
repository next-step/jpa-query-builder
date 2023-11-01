package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

public class SelectQueryBuilder extends QueryBuilder {

    private final AnnotationBinder annotationBinder;
    private final MetadataGenerator metadataGenerator;

    public SelectQueryBuilder(Dialect dialect) {
        super(dialect);
        this.annotationBinder = new AnnotationBinder(dialect);
        this.metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    }

    public Query findAll(Class<?> domain) {
        StringBuilder sb = new StringBuilder();

        StringBuilder queryStringBuilder = sb.append("select * from ")
                .append(getMetaData(domain).getEntity());
        return new Query(queryStringBuilder);
    }

    public Query findById(Class<?> domain, Long id) {
        StringBuilder sb = new StringBuilder();
        StringBuilder queryStringBuilder = sb.append("select * from ")
                .append(getMetaData(domain).getEntity())
                .append(" where ")
                .append(getMetaData(domain).getId())
                .append(" = ")
                .append(id);
        return new Query(queryStringBuilder);
    }

    private MetaData getMetaData(Class<?> domain) {
        return metadataGenerator.generator(domain);
    }
}
