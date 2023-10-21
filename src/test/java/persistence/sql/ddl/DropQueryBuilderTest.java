package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DropQueryBuilderTest {

    @Test
    void dropQueryString() {
        String dropQueryString = DropQueryBuilder.dropQueryString(new MyEntity(Person.class));

        Assertions.assertThat(dropQueryString).isEqualTo("DROP TABLE users");
    }
}
