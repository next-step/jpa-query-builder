package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.meta.service.ClassMetaData;
import persistence.sql.dialect.H2Dialect;

class DDLQueryGeneratorTest {
    private DDLQueryGenerator ddlQueryGenerator;

    @BeforeEach
    void setup() {
        ddlQueryGenerator = new DDLQueryGenerator(new H2Dialect(), ClassMetaData.getInstance());
    }

    @Test
    @DisplayName("create table 쿼리 생성")
    void testCreateTableQuery() {
        String query = ddlQueryGenerator.generateCreateTableQuery(Person.class);
        String expected = "create table users(id bigint generated by default as identity, nick_name varchar, old integer, email varchar not null);";

        assertThat(query).isEqualTo(expected);
    }

    @Test
    @DisplayName("drop table 쿼리 생성")
    void testDropTableQuery() {
        String query = ddlQueryGenerator.generateDropTableQuery(Person.class);
        String expected = "drop table Person if exists;";

        assertThat(query).isEqualTo(expected);
    }
}