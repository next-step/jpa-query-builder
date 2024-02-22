package database.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {
    QueryBuilder builder = QueryBuilder.getInstance();

    @Test
    void singletonTest() {
        QueryBuilder anotherBuilder = QueryBuilder.getInstance();
        assertThat(builder).isEqualTo(anotherBuilder);
    }

    @Test
    void buildCreateQuery() {
        String actual = builder.buildCreateQuery(OldPerson3.class);

        assertThat(actual).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)");
    }

    @Test
    void buildDeleteQuery() {
        String actual = builder.buildDeleteQuery(OldPerson3.class);

        assertThat(actual).isEqualTo("DROP TABLE users");
    }
}
