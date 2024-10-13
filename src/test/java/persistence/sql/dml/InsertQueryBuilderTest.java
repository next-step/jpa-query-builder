package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;
import persistence.fixture.NotEntity;
import persistence.sql.meta.Table;

import static org.assertj.core.api.Assertions.*;

class InsertQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // given
        final Person person = new Person("Jaden", 30, "test@email.com", 1);

        // when
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(person);

        // then
        assertThat(insertQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // given
        final NotEntity notEntity = new NotEntity("Jaden", 30, "test@email.com", 1);

        // when & then
        assertThatThrownBy(() -> new InsertQueryBuilder(notEntity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Table.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("insert 쿼리를 생성한다.")
    void insert() {
        // given
        final Person person = new Person("Jaden", 30, "test@email.com", 1);
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(person);

        // when
        final String query = insertQueryBuilder.insert();

        // then
        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('Jaden', 30, 'test@email.com')");
    }
}
