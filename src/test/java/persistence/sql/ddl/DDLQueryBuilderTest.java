package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.study.sql.ddl.Person1;

import static org.assertj.core.api.Assertions.assertThat;

class DDLQueryBuilderTest {

    private final DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder(new H2Dialect());

    @Test
    @DisplayName("Person1을 이용하여 create 쿼리 생성하기")
    void createBuildQuery() {
        String createQuery = ddlQueryBuilder.buildCreateQuery(Person1.class);
        assertThat(createQuery).isEqualTo("CREATE TABLE person1 (id bigint primary key,name varchar,age integer);");
    }
}
