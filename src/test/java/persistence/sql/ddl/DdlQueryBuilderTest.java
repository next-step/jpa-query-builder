package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @Test
    void should_create_ddl_query() {
        DdlQueryBuilder builder = new MySQLDdlQueryBuilder();

        assertThat(builder.createQuery(Person.class)).isEqualTo("CREATE TABLE Person(" +
                "id BIGINT PRIMARY KEY," +
                " name VARCHAR(255)," +
                " age INT" +
                ");");
    }
}