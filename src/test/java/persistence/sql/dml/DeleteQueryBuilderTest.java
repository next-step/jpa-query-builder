package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.h2.H2Query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(new H2Query());
        assertThatThrownBy(() -> deleteQueryBuilder.getQuery(new TestWithNoEntityAnnotation()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 delete 쿼리를 생성한다.")
    @Test
    void deleteQueryTest() {
        Person entity = new Person(1L, "test1", 10, "test1@gmail.com", 0);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(new H2Query());

        String actualQuery = deleteQueryBuilder.getQuery(entity);

        assertThat(actualQuery).isEqualTo("delete from users where id = 1");
    }

}
