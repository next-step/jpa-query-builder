package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @Test
    void createDdl() {
        String expectedQuery = "create table users (id bigint not null auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id));";
        QueryBuilder queryBuilder = new QueryBuilder();
        String createDdl = queryBuilder.buildCreateDdl(Person.class);

        assertThat(createDdl).isEqualTo(expectedQuery);
    }
}
