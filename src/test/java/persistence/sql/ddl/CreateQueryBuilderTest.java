package persistence.sql.ddl;

import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.*;

class CreateQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void create() {
        // when
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);

        // then
        assertThat(createQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외가 발생한다.")
    void create_exception() {
        // when & then
        assertThatThrownBy(() -> new CreateQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CreateQueryBuilder.CREATE_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("CREATE 쿼리를 생성한다.")
    void build() {
        // given
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);

        // when
        final String query = createQueryBuilder.build();

        // then
        assertThat(query).isEqualTo(
                "CREATE TABLE person ( id BIGINT NOT NULL PRIMARY KEY, name VARCHAR(255), age INTEGER )");
    }

    static public class NotEntity {
        @Id
        private Long id;

        private String name;

        private Integer age;
    }
}
