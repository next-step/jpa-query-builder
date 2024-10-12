package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.NotEntity;
import persistence.sql.Person;
import persistence.sql.QueryBuilder;

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
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외가 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new CreateQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(QueryBuilder.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("DROP 쿼리를 생성한다.")
    void build() {
        // given
        final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);

        // when
        final String query = dropQueryBuilder.build();

        // then
        assertThat(query).isEqualTo("DROP TABLE IF EXISTS users");
    }
}
