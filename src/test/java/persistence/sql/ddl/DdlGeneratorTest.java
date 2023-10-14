package persistence.sql.ddl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlGeneratorTest {
    @Test
    @DisplayName("create 쿼리 생성 테스트")
    void generateCreateDdlTest() {
        final DdlGenerator generator = new DdlGenerator();
        final String query = generator.generateCreateDdl(Person.class);
        assertThat(query).isEqualToIgnoringCase("create table person (id bigint,name varchar,age int,CONSTRAINT PK_Person PRIMARY KEY (id))");
    }
}