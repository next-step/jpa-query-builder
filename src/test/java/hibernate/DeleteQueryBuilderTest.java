package hibernate;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    private final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();

    @Test
    void delete쿼리를_생성한다() {
        // given
        String expected = "delete from test_entity where id = 1;";
        EntityObject givenEntityObject = new EntityObject(new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com"));

        // when
        String actual = deleteQueryBuilder.generateQuery(givenEntityObject)
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
