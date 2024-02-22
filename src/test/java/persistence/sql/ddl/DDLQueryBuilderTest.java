package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.study.sql.ddl.Person1;
import persistence.study.sql.ddl.Person2;

import static org.assertj.core.api.Assertions.assertThat;

class DDLQueryBuilderTest {

    private final DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder(new H2Dialect());

    @Test
    @DisplayName("Person1을 이용하여 create 쿼리 생성하기")
    void createBuildQueryUsingPerson1() {
        String createQuery = ddlQueryBuilder.buildCreateQuery(Person1.class);
        assertThat(createQuery).isEqualTo("CREATE TABLE person1 (id bigint primary key,name varchar,age integer);");
    }

    @Test
    @DisplayName("Person2를 이용하여 create 쿼리 생성하기")
    void createBuildQueryUsingPerson2() {
        String createQuery = ddlQueryBuilder.buildCreateQuery(Person2.class);
        assertThat(createQuery).isEqualTo("CREATE TABLE person2 (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);");
    }
}
