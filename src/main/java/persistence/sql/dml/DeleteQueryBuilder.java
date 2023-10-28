package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

import java.lang.reflect.Field;

public class DeleteQueryBuilder extends QueryBuilder {

    private AnnotationBinder annotationBinder = new AnnotationBinder();
    private MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    public DeleteQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public Query delete(Object domain) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class<?> clazz = domain.getClass();
        String id = getMetaData(clazz).getId();
        Field idField = domain.getClass().getDeclaredField(id);
        idField.setAccessible(Boolean.TRUE);
        StringBuilder deleteQuery = sb.append("delete from ")
                .append(getMetaData(clazz).getEntity())
                .append(" where ")
                .append(id)
                .append(" = ")
                .append(idField.get(domain));
        return new Query(deleteQuery);

    }

    private MetaData getMetaData(Class<?> domain) {
        return metadataGenerator.generator(domain);
    }
}
