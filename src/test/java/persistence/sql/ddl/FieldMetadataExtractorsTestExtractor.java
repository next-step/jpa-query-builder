package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class FieldMetadataExtractorsTestExtractor {

    @Test
    @DisplayName("Person 엔터티 컬럼 정보 가져오기")
    public void getColumnInfoCollectionTest() {
        FieldMetadataExtractors fieldMetaDatas = new FieldMetadataExtractors(Person.class);
        Dialect dialect = new H2Dialect();
        
        assertThat(fieldMetaDatas.getDefinition(dialect))
                .isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR,old INT,email VARCHAR NOT NULL");
    }

}
