package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    @Test
    void should_create_ddl_query() {
        DdlQueryBuilder builder = new MySQLDdlQueryBuilder();

        assertThat(builder.createQuery(Person.class)).isEqualTo("CREATE TABLE users(" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " nick_name VARCHAR(255)," +
                " old INT," +
                " email VARCHAR(255) NOT NULL" +
                ");");
    }
}