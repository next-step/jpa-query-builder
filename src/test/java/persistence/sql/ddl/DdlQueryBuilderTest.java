package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @DisplayName("DDL 쿼리 생성 테스트")
    @Test
    void ddlQueryBuildTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        ddlQueryBuilder = ddlQueryBuilder.create(Person.class);

        assertThat(ddlQueryBuilder.getIdColumns().keySet()).containsExactly("id");
        assertThat(ddlQueryBuilder.getIdColumns().values()).containsExactly(Long.class);
        assertThat(ddlQueryBuilder.getColumns().keySet()).containsExactly("name", "age");
        assertThat(ddlQueryBuilder.getColumns().values()).containsExactly(String.class, Integer.class);
        assertThat(ddlQueryBuilder.build())
                .isEqualTo("create table person (id bigint,name varchar,age int, constraint pk_person primary key (id));");
    }

}
