package persistence.sql.ddl;

import domain.PersonV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.Dialect;
import persistence.dialect.H2Dialect;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

public class CreateTest {

    Dialect dialect = new H2Dialect();
    QueryBuilder queryBuilder = new QueryBuilder(dialect);
    AnnotationBinder annotationBinder = new AnnotationBinder();
    MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);


    @Test
    @DisplayName("요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기")
    void create1Test() {
        MetaData personv1 = metadataGenerator.generator(PersonV1.class);
        StringBuilder sb = new StringBuilder();
        StringBuilder createQuery = queryBuilder.create(personv1, sb);
        System.out.println(createQuery);
    }


}
