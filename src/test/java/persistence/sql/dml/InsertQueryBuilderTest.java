package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.NotEntity;
import persistence.sql.Person;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;

import static org.assertj.core.api.Assertions.*;

class InsertQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // given
        final Person person = new Person("Jaden", 30, "test@email.com", 1);

        // when
        final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(person);

        // then
        assertThat(dropQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // given
        final NotEntity notEntity = new NotEntity("Jaden", 30, "test@email.com", 1);

        // when & then
        assertThatThrownBy(() -> new CreateQueryBuilder(notEntity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(QueryBuilder.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("INSERT 쿼리를 생성한다.")
    void build() {
        // given
        final Person person = new Person("Jaden", 30, "test@email.com", 1);
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(person);

        // when
        final String query = insertQueryBuilder.build();

        // then
        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('Jaden', 30, 'test@email.com')");
    }
}
