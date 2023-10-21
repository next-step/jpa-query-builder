package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateQueryBuilderTest {

    @Test
    void createQueryString() {
        String queryString = CreateQueryBuilder.createQueryString(new MyEntity(Person.class));

        Assertions.assertThat(queryString).isEqualTo("CREATE TABLE users ( \n"
            + " id BIGINT PRIMARY KEY,\n"
            + "name VARCHAR,\n"
            + "age INT,\n"
            + "email VARCHAR \n"
            + " );");
    }
}
