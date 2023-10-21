package persistence.sql.ddl;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {

    @DisplayName("Person 객체를 통해서 drop 쿼리 생성")
    @Test
    void build() {
        //when
        DropQueryBuilder builder = new DropQueryBuilder();
        String query = builder.getQuery(Person.class);

        //then
        assertThat(query).isEqualTo("DROP TABLE users;");
    }
}