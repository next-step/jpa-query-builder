package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.h2.H2Query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindAllQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        FindAllQueryBuilder findAllQueryBuilder = new FindAllQueryBuilder(new H2Query());
        assertThatThrownBy(() -> findAllQueryBuilder.getQuery(TestWithNoEntityAnnotation.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 findAll (select) 쿼리를 생성한다.")
    @Test
    void insertQueryTest() {
        Class<?> entityClass = Person.class;
        FindAllQueryBuilder findAllQueryBuilder = new FindAllQueryBuilder(new H2Query());

        String actualQuery = findAllQueryBuilder.getQuery(entityClass);
        assertThat(actualQuery).isEqualTo("select id, nick_name, old, email from users");
    }

}
