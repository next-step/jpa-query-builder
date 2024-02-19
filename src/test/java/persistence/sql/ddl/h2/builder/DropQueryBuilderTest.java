package persistence.sql.ddl.h2.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class DropQueryBuilderTest {
    @Test
    @DisplayName("@Entity 클래스의 drop 쿼리 만들기")
    void makeDropQuery() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);

        String query = dropQueryBuilder.generateSQL();

        assertThat(query).isEqualTo("drop table users;");
    }
}
