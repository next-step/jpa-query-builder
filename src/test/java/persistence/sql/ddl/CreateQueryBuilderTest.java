package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

class CreateQueryBuilderTest {

    @Test
    void createQueryString() {
        String queryString = CreateQueryBuilder.createQueryString(new MyEntity(Person.class));

        Assertions.assertThat(queryString).isEqualTo("CREATE TABLE users (id BIGINT PRIMARY KEY, name VARCHAR, age INTEGER, email VARCHAR);");
    }
}
