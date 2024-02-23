package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.model.EntityAnalyzer;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person1;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DDLQueryBuilderTest {

    private final DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder(new H2Dialect());

    @DisplayName("Person을 이용하여 create 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildCreateQuery(Class<?> person, String createQuery) {
        EntityAnalyzer analyzer = new EntityAnalyzer(person);
        Table table = new Table(analyzer);

        String result = ddlQueryBuilder.buildCreateQuery(table);

        assertThat(result).isEqualTo(createQuery);
    }

    private static Stream<Arguments> buildCreateQuery() {
        return Stream.of(
                Arguments.arguments(Person1.class, "CREATE TABLE person1 (id bigint primary key,name varchar,age integer);"),
                Arguments.arguments(Person2.class, "CREATE TABLE person2 (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);"),
                Arguments.arguments(Person3.class, "CREATE TABLE users (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);")
        );
    }

    @Test
    @DisplayName("drop 쿼리 생성하기")
    void buildDropQuery() {
        EntityAnalyzer analyzer = new EntityAnalyzer(Person3.class);
        Table table = new Table(analyzer);

        String dropQuery = ddlQueryBuilder.buildDropQuery(table);

        assertThat(dropQuery).isEqualTo("DROP TABLE users;");
    }
}
