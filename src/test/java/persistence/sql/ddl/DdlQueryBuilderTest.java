package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DdlQueryBuilderTest {

    @Test
    void createTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(Person.class);
        String columnsDdl = ddlQueryBuilder.createTable();
        Assertions.assertThat(columnsDdl.trim().toLowerCase()).isEqualTo("create table person (name varchar(255), id bigint, age integer )");
    }

}