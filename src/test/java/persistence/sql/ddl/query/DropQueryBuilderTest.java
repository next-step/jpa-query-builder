package persistence.sql.ddl.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class DropQueryBuilderTest {

    @Test
    @DisplayName("should create a DROP TABLE query")
    void build() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();
        String query = dropQueryBuilder.build(Person.class);

        // Then
        assertThat(query).isEqualTo("DROP TABLE users if exists;");
    }
}
