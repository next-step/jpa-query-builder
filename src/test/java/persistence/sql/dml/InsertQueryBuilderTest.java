package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.h2.H2Query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InsertQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(new H2Query());
        assertThatThrownBy(() -> insertQueryBuilder.getQuery(new TestWithNoEntityAnnotation()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 Insert 쿼리를 생성한다.")
    @Test
    void insertQueryTest() {
        Person entity = new Person("test1", 10, "test1@gmail.com", 0);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(new H2Query());

        String actualQuery = insertQueryBuilder.getQuery(entity);

        assertThat(actualQuery).isEqualTo("insert into users (id, nick_name, old, email) values (default, 'test1', 10, 'test1@gmail.com')");
    }

}
