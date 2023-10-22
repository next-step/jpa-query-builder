package hibernate.dml;

import hibernate.entity.EntityClass;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SelectAllQueryBuilderTest {

    private final SelectAllQueryBuilder selectAllQueryBuilder = new SelectAllQueryBuilder();

    @Test
    void select_all쿼리를_생성한다() {
        // given
        String expected = "select id, nick_name from test_entity;";

        // when
        String actual = selectAllQueryBuilder.generateQuery(new EntityClass<>(TestEntity.class))
                .toLowerCase();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Entity
    @Table(name = "test_entity")
    static class TestEntity {

        @Id
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Transient
        private String email;

        public TestEntity() {
        }

        public TestEntity(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
}
