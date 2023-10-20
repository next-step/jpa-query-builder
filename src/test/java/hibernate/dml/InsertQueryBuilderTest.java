package hibernate.dml;

import hibernate.entity.EntityObject;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    private final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();

    @Test
    void insert쿼리를_생성한다() {
        // given
        TestEntity givenEntity = new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com");
        String expected = "insert into test_entity (id, nick_name) values (1, 최진영);";

        // when
        String actual = insertQueryBuilder.generateQuery(new EntityObject(givenEntity))
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
