package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.NotEntity;
import persistence.sql.Person;
import persistence.sql.QueryBuilder;

import static org.assertj.core.api.Assertions.*;

class SelectQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // when
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);

        // then
        assertThat(selectQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외를 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new SelectQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(QueryBuilder.NOT_ENTITY_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("findAll 쿼리를 생성한다.")
    void findAll() {
        // given
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);

        // when
        final String query = selectQueryBuilder.findAll();

        // then
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    @DisplayName("findById 쿼리를 생성한다.")
    void findById() {
        // given
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);

        // when
        final String query = selectQueryBuilder.findById(1);

        // then
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }

    @Test
    @DisplayName("findById 쿼리를 생성한다.")
    void findById_exception() {
        // given
        final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(NotId.class);

        // when & then
        assertThatThrownBy(() -> selectQueryBuilder.findById(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(QueryBuilder.NOT_ID_FAILED_MESSAGE);
    }

    @Table(name = "users")
    @Entity
    static class NotId {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;

        @Transient
        private Integer index;
    }
}
