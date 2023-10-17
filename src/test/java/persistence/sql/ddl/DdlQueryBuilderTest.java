package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DdlQueryBuilderTest {

    @Test
    void createTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(Person.class);
//        String result = ddlQueryBuilder.createTable();
//        Assertions.assertThat(result).isEqualToIgnoringCase("create person(id bigint primary key , name)");
    }



}