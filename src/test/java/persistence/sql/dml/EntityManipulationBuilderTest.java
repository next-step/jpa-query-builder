package persistence.sql.dml;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.ddl.EntityMetadata;
import persistence.sql.ddl.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class EntityManipulationBuilderTest {

    private static EntityManipulationBuilder entityManipulationBuilder;

    @BeforeAll
    static void setUp() {
        EntityMetadata entityMetadata = EntityMetadata.of(Person.class, new H2Dialect());
        entityManipulationBuilder = new EntityManipulationBuilder(entityMetadata);
    }

    @Test
    @DisplayName("Person 엔터티 insert 쿼리 만들기")
    public void insertQueryTest() {
        Person person = new Person("John Doe",  30, "john.doe@example.com");
        String query = entityManipulationBuilder.insert(person);

        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) " +
                "VALUES ('John Doe', 30, 'john.doe@example.com');");
    }

    @Test
    @DisplayName("Person 엔터티 findAll 쿼리 만들기")
    public void findAllQueryTest() {
        String query = entityManipulationBuilder.findAll(Person.class);

        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users;");
    }

    @Test
    @DisplayName("Person 엔터티 findById 쿼리 만들기")
    public void findByIdQueryTest() {
        long id = 1L;
        String query = entityManipulationBuilder.findById(Person.class, id);

        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = " + id + ";");
    }

    @Test
    @DisplayName("Person 엔터티 delete 쿼리 만들기")
    public void deleteQueryTest() {
        long id = 1L;
        String query = entityManipulationBuilder.delete(Person.class, id);

        assertThat(query).isEqualTo("DELETE FROM users WHERE id = " + id + ";");
    }

}
