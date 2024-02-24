package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;
import persistence.sql.domain.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DropQueryBuilder 의")
class DropQueryBuilderTest {

    @Nested
    @DisplayName("build 메서드는")
    class Build {
        @Test
        @DisplayName("DROP 쿼리를 반환한다.")
        void sqlForCreate() {
            //given
            Class<Person> targetClass = Person.class;

            //when
            String sql = new DropQueryBuilder(new H2Dialect()).build(targetClass);

            //then
            assertThat(sql).isEqualTo("DROP TABLE users");
        }
    }

}
