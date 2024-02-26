package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Person을 이용하여 create 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildCreateQuery(DDLQueryBuilder ddlQueryBuilder, String createQuery) {

        String result = ddlQueryBuilder.buildCreateQuery();

        assertThat(result).isEqualTo(createQuery);
    }

    private static Stream<Arguments> buildCreateQuery() {
        H2Dialect dialect = new H2Dialect();

        Table person1Table = new Table(Person1.class);
        Table person2Table = new Table(Person2.class);
        Table person3Table = new Table(Person3.class);

        DDLQueryBuilder person1DDLQueryBuilder = new DDLQueryBuilder(person1Table, dialect);
        DDLQueryBuilder person2DDLQueryBuilder = new DDLQueryBuilder(person2Table, dialect);
        DDLQueryBuilder person3DDLQueryBuilder = new DDLQueryBuilder(person3Table, dialect);

        return Stream.of(
                Arguments.arguments(person1DDLQueryBuilder, "CREATE TABLE person1 (id bigint primary key,name varchar,age integer);"),
                Arguments.arguments(person2DDLQueryBuilder, "CREATE TABLE person2 (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);"),
                Arguments.arguments(person3DDLQueryBuilder, "CREATE TABLE users (id bigint auto_increment primary key,nick_name varchar,old integer,email varchar not null);")
        );
    }

    @DisplayName("Person을 이용하여 drop 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildDropQuery(DDLQueryBuilder ddlQueryBuilder, String dropQuery) {
        String result = ddlQueryBuilder.buildDropQuery();

        assertThat(result).isEqualTo(dropQuery);
    }

    private static Stream<Arguments> buildDropQuery() {
        H2Dialect dialect = new H2Dialect();

        Table person1Table = new Table(Person1.class);
        Table person2Table = new Table(Person2.class);
        Table person3Table = new Table(Person3.class);

        DDLQueryBuilder person1DDLQueryBuilder = new DDLQueryBuilder(person1Table, dialect);
        DDLQueryBuilder person2DDLQueryBuilder = new DDLQueryBuilder(person2Table, dialect);
        DDLQueryBuilder person3DDLQueryBuilder = new DDLQueryBuilder(person3Table, dialect);

        return Stream.of(
                Arguments.arguments(person1DDLQueryBuilder, "DROP TABLE person1;"),
                Arguments.arguments(person2DDLQueryBuilder, "DROP TABLE person2;"),
                Arguments.arguments(person3DDLQueryBuilder, "DROP TABLE users;")
        );
    }
}
