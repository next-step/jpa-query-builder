package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @Test
    void createDdl() {
        String expectedQuery = "create table person (id bigint not null, name varchar(255), age integer, primary key (id));";
        QueryBuilder queryBuilder = new QueryBuilder();
        String createDdl = queryBuilder.buildCreateDdl(Person.class);

        assertThat(createDdl).isEqualTo(expectedQuery);
    }
}
