package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    @Test
    void build() {
        //given
        Person changgunyee = new Person(1L, "changgunyee", 29);

        //when
        CreateQueryBuilder builder = new CreateQueryBuilder();
        String query = builder.build(changgunyee);

        //then
        assertThat(query).isEqualToIgnoringWhitespace("" +
                "CREATE TABLE Person (\n" +
                "        id BIGINT,\n" +
                "        name VARCHAR(50),\n" +
                "        age INT,\n" +
                "        PRIMARY KEY(id)\n" +
                "    );");
    }
}