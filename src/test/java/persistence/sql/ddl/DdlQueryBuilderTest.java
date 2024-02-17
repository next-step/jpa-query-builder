package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DdlQueryBuilderTest {

    private final DdlQueryBuilder builder = new MySQLDdlQueryBuilder();

    @Test
    void should_create_create_query() {
        assertThat(builder.createQuery(Person.class)).isEqualTo("CREATE TABLE users(" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " nick_name VARCHAR(255)," +
                " old INT," +
                " email VARCHAR(255) NOT NULL" +
                ");");
    }

    @Test
    void should_create_drop_query() {
        assertThat(builder.dropQuery(Person.class)).isEqualTo("DROP TABLE users;");
    }
}