package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.NotEntity;
import persistence.sql.Person;
import persistence.sql.QueryBuilder;

import static org.assertj.core.api.Assertions.*;

class CreateQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // when
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);

        // then
        assertThat(createQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new CreateQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(QueryBuilder.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("create 쿼리를 생성한다.")
    void create() {
        // given
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);

        // when
        final String query = createQueryBuilder.create();

        // then
        assertThat(query).isEqualTo(
                "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
    }
}
