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
        // given
        final Person person = new Person("Jaden", 30, "test@email.com", 1);

        // when
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(person);

        // then
        assertThat(createQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외가 발생한다.")
    void constructor_exception() {
        // given
        final NotEntity notEntity = new NotEntity("Jaden", 30, "test@email.com", 1);

        // when & then
        assertThatThrownBy(() -> new CreateQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(QueryBuilder.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("CREATE 쿼리를 생성한다.")
    void build() {
        // given
        final Person person = new Person("Jaden", 30, "test@email.com", 1);
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(person);

        // when
        final String query = createQueryBuilder.build();

        // then
        assertThat(query).isEqualTo(
                "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
    }
}
