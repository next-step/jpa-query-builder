package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;
import persistence.fixture.NotEntity;
import persistence.sql.meta.Table;

import static org.assertj.core.api.Assertions.*;

class DeleteQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // when
        final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(Person.class);

        // then
        assertThat(deleteQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new DeleteQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Table.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("delete 쿼리를 생성한다.")
    void delete() {
        // given
        final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(Person.class);

        // when
        final String query = deleteQueryBuilder.delete(1);

        // then
        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1");
    }
}
