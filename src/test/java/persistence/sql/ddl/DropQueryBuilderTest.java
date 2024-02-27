package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.Person;
import persistence.sql.ddl.entity.Person3;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {

    @Test
    @DisplayName("Person 클래스를 이용한 DDL 삭제 테스트")
    void DDLDropTest() {
        // given
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);
        String expectedQuery = "DROP TABLE Person;";

        // when
        String actualQuery = dropQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Table 선언된 Person3 클래스를 이용한 DDL 삭제 테스트")
    void DDLDropTest2() {
        // given
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person3.class);
        String expectedQuery = "DROP TABLE users;";

        // when
        String actualQuery = dropQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}
