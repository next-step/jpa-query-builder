package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.model.EntityAnalyzer;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person1;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import static org.assertj.core.api.Assertions.assertThat;

class DDLQueryBuilderTest {

    private final DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder(new H2Dialect());

    @Test
    @DisplayName("Person1을 이용하여 create 쿼리 생성하기")
    void buildCreateQueryUsingPerson1() {
        EntityAnalyzer analyzer = new EntityAnalyzer(Person1.class);
        Table table = new Table(analyzer);

        String createQuery = ddlQueryBuilder.buildCreateQuery(table);

        assertThat(createQuery).isEqualTo("CREATE TABLE person1 (id bigint primary key,name varchar,age integer);");
    }

    @Test
    @DisplayName("Person2를 이용하여 create 쿼리 생성하기")
    void buildCreateQueryUsingPerson2() {
        EntityAnalyzer analyzer = new EntityAnalyzer(Person2.class);
        Table table = new Table(analyzer);

        String createQuery = ddlQueryBuilder.buildCreateQuery(table);

        assertThat(createQuery).isEqualTo("CREATE TABLE person2 (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);");
    }

    @Test
    @DisplayName("Person3를 이용하여 create 쿼리 생성하기")
    void buildCreateQueryUsingPerson3() {
        EntityAnalyzer analyzer = new EntityAnalyzer(Person3.class);
        Table table = new Table(analyzer);

        String createQuery = ddlQueryBuilder.buildCreateQuery(table);

        assertThat(createQuery).isEqualTo("CREATE TABLE person3 (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);");
    }

    @Test
    @DisplayName("drop 쿼리 생성하기")
    void buildDropQuery() {
        EntityAnalyzer analyzer = new EntityAnalyzer(Person3.class);
        Table table = new Table(analyzer);

        String dropQuery = ddlQueryBuilder.buildDropQuery(table);

        assertThat(dropQuery).isEqualTo("DROP TABLE person3;");
    }
}
