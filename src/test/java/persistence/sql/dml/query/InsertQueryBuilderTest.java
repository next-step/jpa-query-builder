package persistence.sql.dml.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InsertQueryBuilderTest {
    private final InsertQueryBuilder queryBuilder = new InsertQueryBuilder();

    @Test
    @DisplayName("Should build insert users query")
    void shouldBuildInsertUsersQuery() {
        Person person = new Person(1L, "john_doe", 30, "chanho0912@gmail.com", 1);

        String query = queryBuilder.build(person);

        assertThat(query).isEqualTo("INSERT INTO users (id, nick_name, old, email) VALUES (1, 'john_doe', 30, 'chanho0912@gmail.com');");
    }

    @Entity
    private static class HasNullableColumnEntity {
        @Id
        private Long id;

        private String name;

        private Integer age;

        public HasNullableColumnEntity() {
        }

        public HasNullableColumnEntity(Long id) {
            this.id = id;
        }

        public HasNullableColumnEntity(Long id, Integer age) {
            this.id = id;
            this.age = age;
        }
    }

    @Test
    void testIgnoreNullableColumnInInsertQuery() {
        HasNullableColumnEntity hasNullableColumnEntity1 = new HasNullableColumnEntity(1L);
        HasNullableColumnEntity hasNullableColumnEntity2 = new HasNullableColumnEntity(2L, 10);

        String query1 = queryBuilder.build(hasNullableColumnEntity1);
        String query2 = queryBuilder.build(hasNullableColumnEntity2);

        assertAll(
                () -> assertThat(query1).isEqualTo("INSERT INTO HasNullableColumnEntity (id) VALUES (1);"),
                () -> assertThat(query2).isEqualTo("INSERT INTO HasNullableColumnEntity (id, age) VALUES (2, 10);")
        );
    }
}
