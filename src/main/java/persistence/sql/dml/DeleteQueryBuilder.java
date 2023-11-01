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

    private AnnotationBinder annotationBinder = new AnnotationBinder(dialect);
    private MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    public DeleteQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public Query queryForObject(Object domain) {
        StringBuilder sb = new StringBuilder();
        Class<?> clazz = domain.getClass();
        String id = getMetaData(clazz).getId();
        Field idField = null;
        try {
            idField = domain.getClass().getDeclaredField(id);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        idField.setAccessible(Boolean.TRUE);
        StringBuilder deleteQuery = null;
        try {
            deleteQuery = sb.append("delete from ")
                    .append(getMetaData(clazz).getEntity())
                    .append(" where ")
                    .append(id)
                    .append(" = ")
                    .append(idField.get(domain));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return new Query(deleteQuery);
    }

    private MetaData getMetaData(Class<?> domain) {
        return metadataGenerator.generator(domain);
    }
}
