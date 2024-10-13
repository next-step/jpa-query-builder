package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;
import persistence.fixture.NotEntity;
import persistence.sql.meta.Table;

import static org.assertj.core.api.Assertions.*;

class DropQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // when
        final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);

        // then
        assertThat(dropQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new CreateQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Table.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("drop 쿼리를 생성한다.")
    void drop() {
        // given
        final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);

        // when
        final String query = dropQueryBuilder.drop();

        // then
        assertThat(query).isEqualTo("DROP TABLE IF EXISTS users");
    }
}
