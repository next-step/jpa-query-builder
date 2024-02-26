package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.dialect.H2Dialect;
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
        Table table = new Table(person);

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

    @DisplayName("Person을 이용하여 drop 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildDropQuery(Class<?> person, String dropQuery) {
        Table table = new Table(person);

        String result = ddlQueryBuilder.buildDropQuery(table);

        assertThat(result).isEqualTo(dropQuery);
    }

    private static Stream<Arguments> buildDropQuery() {
        return Stream.of(
                Arguments.arguments(Person1.class, "DROP TABLE person1;"),
                Arguments.arguments(Person2.class, "DROP TABLE person2;"),
                Arguments.arguments(Person3.class, "DROP TABLE users;")
        );
    }
}
